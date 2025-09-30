package org.example.accountprocessing.repository;

import org.example.accountprocessing.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByAccountIdAndPaymentDate(Long accountId, LocalDate paymentDate);

    List<Payment> findAllByAccountId(Long accountId);
}
