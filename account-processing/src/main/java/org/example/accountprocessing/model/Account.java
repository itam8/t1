package org.example.accountprocessing.model;

import jakarta.persistence.*;
import lombok.Data;
import org.example.clientprocessing.model.Client;
import org.example.clientprocessing.model.Product;

@Data
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private Double balance;
    @Column(name = "interest_rate")
    private Double interestRate;
    @Column(name = "is_recalc")
    private Boolean isRecalc;
    @Column(name = "card_exist")
    private Boolean cardExist;
    @Enumerated(EnumType.STRING)
    private Status status;
}
