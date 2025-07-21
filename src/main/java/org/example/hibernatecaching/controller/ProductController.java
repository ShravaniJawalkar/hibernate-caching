package org.example.hibernatecaching.controller;

import org.example.hibernatecaching.model.Product;
import org.example.hibernatecaching.model.ProductRequest;
import org.example.hibernatecaching.model.ProductResponse;
import org.example.hibernatecaching.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequest product) {
        return productService.save(product);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestParam(value = "name", required = true) String name) {
        return productService.update(id, name);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        return productService.delete(id);
    }

    @PutMapping("/{id}/order")
    public ResponseEntity<String> addOrderToProduct(@PathVariable Long id, @RequestParam("order_id") Long orderId, @RequestParam("order_date") LocalDateTime orderDate) {
        return productService.addOrderToProduct(id, orderId, orderDate);
    }
}

