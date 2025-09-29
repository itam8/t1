package org.example.clientprocessing.service;

import lombok.AllArgsConstructor;
import org.example.clientprocessing.model.Product;
import org.example.clientprocessing.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product findById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new IllegalStateException("Продукта с таким id не существует");
        }
        return optionalProduct.get();
    }

    public Product create(Product product) {
        if (productRepository.existsByName(product.getName())) {
            throw new IllegalStateException("Продукт с таким названием уже существует");
        }
        return productRepository.save(product);
    }

    public void update(Product product) {
        if (!productRepository.existsById(product.getId())) {
            throw new IllegalStateException("Продукта с таким id не существует");
        }
        productRepository.save(product);
    }

    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalStateException("Продукта с таким id не существует");
        }
        productRepository.deleteById(id);
    }
}
