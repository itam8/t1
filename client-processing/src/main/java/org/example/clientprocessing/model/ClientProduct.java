package org.example.clientprocessing.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "client_products")
public class ClientProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @Column(name = "open_date")
    private LocalDate openDate;
    @Column(name = "close_date")
    private LocalDate closeDate;
    @Enumerated(EnumType.STRING)
    private Status status;
}
