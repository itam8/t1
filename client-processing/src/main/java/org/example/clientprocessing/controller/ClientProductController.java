package org.example.clientprocessing.controller;

import lombok.AllArgsConstructor;
import org.example.clientprocessing.dto.ClientProductDto;
import org.example.clientprocessing.dto.ProductRegistryDto;
import org.example.clientprocessing.kafka.KafkaProducer;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client-products")
@AllArgsConstructor
public class ClientProductController {
    private final KafkaProducer kafkaProducer;

    @PostMapping
    public void sendClientProductMessage(@RequestBody ClientProductDto clientProductDto) {
        kafkaProducer.sendClientProductMessage(clientProductDto);
    }

    @PostMapping
    public void sendClientCreditProductMessage(@RequestBody ProductRegistryDto productRegistryDto) {
        kafkaProducer.sendClientCreditProductMessage(productRegistryDto);
    }

    @PostMapping("/cards")
    public void sendClientCardMessage(@RequestBody ClientProductDto clientProductDto) {
        kafkaProducer.sendClientCardMessage(clientProductDto);
    }
}
