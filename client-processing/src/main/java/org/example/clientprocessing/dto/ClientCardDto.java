package org.example.clientprocessing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientCardDto {
    private Long clientId;
    private Long productId;
    private String paymentSystem;
}
