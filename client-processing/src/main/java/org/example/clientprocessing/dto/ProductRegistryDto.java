package org.example.clientprocessing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRegistryDto {
    private Long clientId;
    private Long accountId;
    private Long productId;
    private Double interestRate;
    private Integer monthCount;
    private Double amount;
}
