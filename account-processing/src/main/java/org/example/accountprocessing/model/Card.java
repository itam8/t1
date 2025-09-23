package org.example.accountprocessing.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    @Column(name = "card_id")
    private String cardId;
    @Column(name = "payment_system")
    private String paymentSystem;
    @Enumerated(EnumType.STRING)
    private Status status;
}
