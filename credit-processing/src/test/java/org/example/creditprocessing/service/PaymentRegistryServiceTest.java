package org.example.creditprocessing.service;

import org.example.creditprocessing.model.PaymentRegistry;
import org.example.creditprocessing.repository.PaymentRegistryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PaymentRegistryServiceTest {
    @InjectMocks
    private PaymentRegistryService paymentRegistryService;
    @Mock
    private PaymentRegistryRepository paymentRegistryRepository;

    @Test
    void findAllByClientId() {
        long clientId = 1L;
        List<PaymentRegistry> paymentRegistries = Collections.emptyList();

        Mockito.when(paymentRegistryRepository.findAllByClientId(clientId)).thenReturn(paymentRegistries);

        Assertions.assertEquals(paymentRegistries, paymentRegistryService.findAllByClientId(clientId));
    }
}
