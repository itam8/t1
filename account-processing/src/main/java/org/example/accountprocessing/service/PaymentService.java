package org.example.accountprocessing.service;

import lombok.AllArgsConstructor;
import org.example.accountprocessing.model.Payment;
import org.example.accountprocessing.model.Transaction;
import org.example.accountprocessing.repository.PaymentRepository;
import org.example.accountprocessing.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public Payment findByAccountIdAndPaymentDate(Long accountId, LocalDate paymentDate) {
        Optional<Payment> optionalPayment = paymentRepository.findByAccountIdAndPaymentDate(accountId, paymentDate);
        if (optionalPayment.isEmpty()) {
            throw new IllegalStateException("Такого платежа не существует");
        }

        return optionalPayment.get();
    }

    public List<Payment> findAllByAccountId(Long accountId) {
        return paymentRepository.findAllByAccountId(accountId);
    }

    public Payment create(Payment payment) {
        return paymentRepository.save(payment);
    }
}
