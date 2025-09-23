package org.example.accountprocessing.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;
    @Enumerated(EnumType.STRING)
    private Type type;
    private Double amount;
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    private LocalDateTime timestamp;
}
