package org.example.creditprocessing.repository;

import org.example.creditprocessing.model.PaymentRegistry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentRegistryRepository extends JpaRepository<PaymentRegistry, Long> {
    @Query(value = "SELECT pay.id, pay.product_registry_id, pay.payment_date, pay.amount, " +
            "pay.interest_rate_amount, pay.expired, pay.payment_expiration_date FROM payment_registries pay " +
            "JOIN product_registries pr ON pay.product_registry_id = pr.id " +
            "WHERE pr.client_id = :clientId", nativeQuery = true)
    List<PaymentRegistry> findAllByClientId(Long clientId);
}
