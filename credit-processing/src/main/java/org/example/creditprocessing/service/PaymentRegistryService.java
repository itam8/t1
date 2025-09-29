package org.example.creditprocessing.service;

import lombok.AllArgsConstructor;
import org.example.creditprocessing.model.PaymentRegistry;
import org.example.creditprocessing.repository.PaymentRegistryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PaymentRegistryService {
    private final PaymentRegistryRepository paymentRegistryRepository;

    public List<PaymentRegistry> findAllByClientId(Long clientId) {
        return paymentRegistryRepository.findAllByClientId(clientId);
    }
}
