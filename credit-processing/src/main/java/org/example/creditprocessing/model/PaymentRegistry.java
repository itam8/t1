package org.example.creditprocessing.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "payment_registries")
public class PaymentRegistry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_registry_id")
    private ProductRegistry productRegistry;
    @Column(name = "paymend_date")
    private LocalDate paymendDate;
    private Double amount;
    @Column(name = "interest_rate_amount")
    private Double interestRateAmount;
    @Column(name = "debt_amount")
    private Double debtAmount;
    private Boolean expired;
    @Column(name = "payment_expiration_date")
    private LocalDate paymentExpirationDate;
}
