package org.example.clientprocessing.kafka;

import lombok.AllArgsConstructor;
import org.example.clientprocessing.dto.ClientCardDto;
import org.example.clientprocessing.dto.ClientProductDto;
import org.example.clientprocessing.dto.ProductRegistryDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendClientProductMessage(ClientProductDto message) {
        kafkaTemplate.send("client_products", message);
    }

    public void sendClientCreditProductMessage(ProductRegistryDto message) {
        kafkaTemplate.send("client_credit_products", message);
    }

    public void sendClientCardMessage(ClientCardDto message) {
        kafkaTemplate.send("client_cards", message);
    }
}
