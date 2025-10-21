package org.example.clientprocessing.service;

import org.example.clientprocessing.model.Key;
import org.example.clientprocessing.model.Product;
import org.example.clientprocessing.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;

    @Test
    void findById_withValidId() {
        long id = 1L;
        Product product = new Product();
        product.setProductKey(Key.DC);
        Optional<Product> optionalProduct = Optional.of(product);

        Mockito.when(productRepository.findById(id)).thenReturn(optionalProduct);

        Assertions.assertEquals(product, productService.findById(id));
    }

    @Test
    void findById_withInvalidId() {
        long id = 1L;
        Optional<Product> optionalProduct = Optional.empty();

        Mockito.when(productRepository.findById(id)).thenReturn(optionalProduct);

        Assertions.assertThrows(IllegalStateException.class, () -> productService.findById(id));
    }

    @Test
    void create_withValidName() {
        Product product = new Product();
        Mockito.when(productRepository.existsByName(product.getName())).thenReturn(false);
        Mockito.when(productRepository.save(product)).thenReturn(product);

        productService.create(product);

        Mockito.verify(productRepository, Mockito.times(1)).save(product);
    }

    @Test
    void create_withInvalidName() {
        Product product = new Product();
        Mockito.when(productRepository.existsByName(product.getName())).thenReturn(true);

        Assertions.assertThrows(IllegalStateException.class, () -> productService.create(product));
    }

    @Test
    void update_withValidId() {
        Product product = new Product();
        Mockito.when(productRepository.existsById(product.getId())).thenReturn(true);
        Mockito.when(productRepository.save(product)).thenReturn(product);

        productService.update(product);

        Mockito.verify(productRepository, Mockito.times(1)).save(product);
    }

    @Test
    void update_withInvalidId() {
        Product product = new Product();
        Mockito.when(productRepository.existsById(product.getId())).thenReturn(false);

        Assertions.assertThrows(IllegalStateException.class, () -> productService.update(product));
    }

    @Test
    void delete_withValidId() {
        long id = 1L;
        Mockito.when(productRepository.existsById(id)).thenReturn(true);

        productService.delete(id);

        Mockito.verify(productRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    void delete_withInvalidId() {
        long id = 1L;
        Mockito.when(productRepository.existsById(id)).thenReturn(false);

        Assertions.assertThrows(IllegalStateException.class, () -> productService.delete(id));
    }
}
