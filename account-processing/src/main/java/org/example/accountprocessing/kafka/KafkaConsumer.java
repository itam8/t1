package org.example.accountprocessing.kafka;

import lombok.AllArgsConstructor;
import org.example.accountprocessing.dto.ClientProductDto;
import org.example.accountprocessing.service.AccountService;
import org.example.accountprocessing.service.CardService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KafkaConsumer {
    private final AccountService accountService;
    private final CardService cardService;

    @KafkaListener(topics = "client_products", groupId = "account-processing")
    public void listenClientProducts(ClientProductDto message) {
        accountService.create(message);
    }

    @KafkaListener(topics = "client_transactions", groupId = "account-processing")
    public void listenClientTransactions(String message) {

    }

    @KafkaListener(topics = "client_cards", groupId = "account-processing")
    public void listenClientCards(ClientProductDto message) {
        cardService.create(message);
    }
}
