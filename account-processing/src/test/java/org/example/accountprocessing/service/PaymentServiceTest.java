package org.example.accountprocessing.service;

import org.example.accountprocessing.model.Payment;
import org.example.accountprocessing.repository.PaymentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {
    @InjectMocks
    private PaymentService paymentService;
    @Mock
    private PaymentRepository paymentRepository;

    @Test
    void findByAccountIdAndPaymentDate_withValidAccountIdAndPaymentDate() {
        Payment payment = new Payment();
        Optional<Payment> optionalPayment = Optional.of(payment);

        Mockito.when(paymentRepository.findByAccountIdAndPaymentDate(payment.getAccountId(), payment.getPaymentDate()))
                .thenReturn(optionalPayment);

        Assertions.assertEquals(
                payment, paymentService.findByAccountIdAndPaymentDate(payment.getAccountId(), payment.getPaymentDate())
        );
    }

    @Test
    void findByAccountIdAndPaymentDate_withInvalidAccountIdAndPaymentDate() {
        Payment payment = new Payment();
        Optional<Payment> optionalPayment = Optional.empty();

        Mockito.when(paymentRepository.findByAccountIdAndPaymentDate(payment.getAccountId(), payment.getPaymentDate()))
                .thenReturn(optionalPayment);

        Assertions.assertThrows(
                IllegalStateException.class,
                () -> paymentService.findByAccountIdAndPaymentDate(payment.getAccountId(), payment.getPaymentDate())
        );
    }

    @Test
    void findAllByAccountId() {
        long accountId = 1L;
        List<Payment> payments = Collections.emptyList();

        Mockito.when(paymentRepository.findAllByAccountId(accountId)).thenReturn(payments);

        Assertions.assertEquals(payments, paymentService.findAllByAccountId(accountId));
    }

    @Test
    void create() {
        Payment payment = new Payment();

        Mockito.when(paymentRepository.save(payment)).thenReturn(payment);

        Assertions.assertEquals(payment, paymentService.create(payment));
    }
}
