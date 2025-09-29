package org.example.accountprocessing.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "client_id")
    private Long clientId;
    @Column(name = "product_id")
    private Long productId;
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
