package org.example.creditprocessing.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_registries")
public class ProductRegistry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "client_id")
    private Long clientId;
    @Column(name = "account_id")
    private Long accountId;
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "interest_rate")
    private Double interestRate;
    @Column(name = "opened_date")
    private LocalDate openedDate;
    @Column(name = "month_count")
    private Integer monthCount;
}
