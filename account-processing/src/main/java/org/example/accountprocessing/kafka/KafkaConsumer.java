package org.example.accountprocessing.kafka;

import lombok.AllArgsConstructor;
import org.example.accountprocessing.config.AccountProcessingConfig;
import org.example.accountprocessing.dto.ClientCardDto;
import org.example.accountprocessing.dto.ClientProductDto;
import org.example.accountprocessing.model.*;
import org.example.accountprocessing.service.AccountService;
import org.example.accountprocessing.service.CardService;
import org.example.accountprocessing.service.PaymentService;
import org.example.accountprocessing.service.TransactionService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class KafkaConsumer {
    private final AccountService accountService;
    private final CardService cardService;
    private final TransactionService transactionService;
    private final PaymentService paymentService;
    private final AccountProcessingConfig accountProcessingConfig;

    @KafkaListener(
            topics = "client_products",
            groupId = "account-processing",
            containerFactory = "clientProductsKafkaListenerContainerFactory"
    )
    public void listenClientProducts(ClientProductDto message) {
        accountService.create(message);
    }

    @Transactional
    @KafkaListener(
            topics = "client_transactions",
            groupId = "account-processing",
            containerFactory = "clientTransactionsKafkaListenerContainerFactory"
    )
    public void listenClientTransactions(Transaction message) {
        Account account = accountService.findById(message.getAccountId());
        LocalDateTime timestamp = message.getTimestamp();
        List<Transaction> transactions = transactionService.findAllByCardIdAndTimestampBetween(
                message.getCardId(), timestamp.minus(accountProcessingConfig.getMaxPeriod()), timestamp
        );

        if (account.getStatus() != Status.OPENED && account.getStatus() != Status.ACTIVE) {
            return;
        }
        if (transactions.size() > accountProcessingConfig.getMaxTransactionsCount()) {
            blockAccountAndTransactions(account, transactions);
            return;
        }

        if (message.getType() == Type.DEPOSIT) {
            if (account.getIsRecalc()) {
                debitingMonthlyAmountFromBalance(account, message);
            } else {
                account.setBalance(account.getBalance() + message.getAmount());
            }
        } else {
            account.setBalance(account.getBalance() - message.getAmount());
        }
    }

    private void blockAccountAndTransactions(Account account, List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            transaction.setStatus(TransactionStatus.BLOCKED);
        }
        account.setStatus(Status.FROZEN);
    }

    private void debitingMonthlyAmountFromBalance(Account account, Transaction transaction) {
        Payment payment = paymentService
                .findByAccountIdAndPaymentDate(account.getId(), LocalDate.from(transaction.getTimestamp()));
        if (transaction.getAmount() >= payment.getAmount()) {
            account.setBalance(account.getBalance() - payment.getAmount());
        } else {
            payment.setExpired(true);
        }
    }

    @KafkaListener(
            topics = "client_cards",
            groupId = "account-processing",
            containerFactory = "clientCardsKafkaListenerContainerFactory"
    )
    public void listenClientCards(ClientCardDto message) {
        cardService.create(message);
    }

    @Transactional
    @KafkaListener(
            topics = "client_payments",
            groupId = "account-processing",
            containerFactory = "clientPaymentsKafkaListenerContainerFactory"
    )
    public void listenClientPayments(Payment message) {
        Account account = accountService.findById(message.getAccountId());

        if (message.getAmount().equals(account.getBalance())) {
            account.setBalance(0.0);

            LocalDateTime now = LocalDateTime.now();
            paymentService.create(new Payment(
                    null,
                    account.getId(),
                    message.getPaymentDate(),
                    message.getAmount(),
                    message.getIsCredit(),
                    now,
                    message.getType(),
                    message.getExpired()
            ));

            List<Payment> payments = paymentService.findAllByAccountId(account.getId());
            for (Payment payment : payments) {
                payment.setPayedAt(now);
            }
        }
    }
}
