package org.example.creditprocessing.service;

import org.example.creditprocessing.dto.ProductRegistryDto;
import org.example.creditprocessing.model.ProductRegistry;
import org.example.creditprocessing.repository.ProductRegistryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductRegistryServiceTest {
    @InjectMocks
    private ProductRegistryService productRegistryService;
    @Mock
    private ProductRegistryRepository productRegistryRepository;

    @Test
    void create() {
        ProductRegistryDto productRegistryDto = new ProductRegistryDto();
        ProductRegistry productRegistry = new ProductRegistry();

        Mockito.when(productRegistryRepository.save(productRegistry)).thenReturn(productRegistry);

        Assertions.assertEquals(productRegistry, productRegistryService.create(productRegistryDto));
    }
}
