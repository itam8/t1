package org.example.creditprocessing.model;

import jakarta.persistence.*;
import lombok.Data;
import org.example.accountprocessing.model.Account;
import org.example.clientprocessing.model.Client;
import org.example.clientprocessing.model.Product;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "product_registries")
public class ProductRegistry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @Column(name = "interest_rate")
    private Double interestRate;
    @Column(name = "opened_date")
    private LocalDate openedDate;
}
