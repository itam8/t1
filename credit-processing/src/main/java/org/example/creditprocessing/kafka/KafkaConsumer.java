package org.example.creditprocessing.kafka;

import lombok.AllArgsConstructor;
import org.example.creditprocessing.config.CreditProcessingConfig;
import org.example.creditprocessing.dto.ClientDto;
import org.example.creditprocessing.dto.ProductRegistryDto;
import org.example.creditprocessing.model.PaymentRegistry;
import org.example.creditprocessing.service.PaymentRegistryService;
import org.example.creditprocessing.service.ProductRegistryService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@AllArgsConstructor
public class KafkaConsumer {
    private final RestTemplate restTemplate;
    private final CreditProcessingConfig creditProcessingConfig;
    private final PaymentRegistryService paymentRegistryService;
    private final ProductRegistryService productRegistryService;

    @KafkaListener(topics = "client_credit_products", groupId = "credit-processing")
    public void listenClientCreditProducts(ProductRegistryDto message) {
        String url = "http://localhost:8080/clients?id=" + message.getClientId();
        ClientDto clientDto = restTemplate.getForObject(url, ClientDto.class);

        if (isSuitableClient(message.getClientId(), message.getAmount())) {
            productRegistryService.create(message);
        }
    }

    private boolean isSuitableClient(long clientId, double requestedAmount) {
        List<PaymentRegistry> paymentRegistries = paymentRegistryService.findAllByClientId(clientId);
        double sum = paymentRegistries.stream().mapToDouble(PaymentRegistry::getDebtAmount).sum();
        boolean isExpired = paymentRegistries.stream().anyMatch(PaymentRegistry::getExpired);

        return !isExpired && sum + requestedAmount <= creditProcessingConfig.getMaxCreditLimit();
    }
}
