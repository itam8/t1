package org.example.clientprocessing.controller;

import lombok.AllArgsConstructor;
import org.example.clientprocessing.model.Product;
import org.example.clientprocessing.service.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public Product create(@RequestBody Product product) {
        return productService.create(product);
    }

    @PutMapping
    public void update(@RequestBody Product product) {
        productService.update(product);
    }

    @DeleteMapping
    public void delete(@RequestParam Long id) {
        productService.delete(id);
    }
}
