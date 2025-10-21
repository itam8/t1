package org.example.clientprocessing.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "product_key")
    private Key productKey;
    @Column(name = "create_date")
    private LocalDate createDate;
    @Transient
    private String productId;

    public String getProductId() {
        if (this.productId == null) {
            String var10001 = this.productKey.name();
            this.productId = var10001 + this.id;
        }

        return this.productId;
    }
}
