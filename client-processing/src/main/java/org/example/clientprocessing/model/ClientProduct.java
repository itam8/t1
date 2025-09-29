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
    @Column(name = "client_id")
    private Long clientId;
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "open_date")
    private LocalDate openDate;
    @Column(name = "close_date")
    private LocalDate closeDate;
    @Enumerated(EnumType.STRING)
    private Status status;
}
