package org.example.accountprocessing.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    @Column(name = "payment_date")
    private LocalDate paymentDate;
    private Double amount;
    @Column(name = "is_credit")
    private Boolean isCredit;
    @Column(name = "payed_at")
    private String payedAt;
    @Enumerated(EnumType.STRING)
    private Type type;
}
