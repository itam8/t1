package org.example.creditprocessing.service;

import lombok.AllArgsConstructor;
import org.example.creditprocessing.dto.ProductRegistryDto;
import org.example.creditprocessing.model.ProductRegistry;
import org.example.creditprocessing.repository.ProductRegistryRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductRegistryService {
    private final ProductRegistryRepository productRegistryRepository;

    public ProductRegistry create(ProductRegistryDto productRegistryDto) {
        return productRegistryRepository.save(new ProductRegistry(
                null,
                productRegistryDto.getClientId(),
                productRegistryDto.getAccountId(),
                productRegistryDto.getProductId(),
                productRegistryDto.getInterestRate(),
                null,
                productRegistryDto.getMonthCount()
        ));
    }
}
