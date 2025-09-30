package org.example.accountprocessing.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "account_id")
    private Long accountId;
    @Column(name = "payment_date")
    private LocalDate paymentDate;
    private Double amount;
    @Column(name = "is_credit")
    private Boolean isCredit;
    @Column(name = "payed_at")
    private LocalDateTime payedAt;
    @Enumerated(EnumType.STRING)
    private Type type;
    private Boolean expired;
}
