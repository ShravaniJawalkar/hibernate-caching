package org.example.hibernatecaching.service;

import org.example.hibernatecaching.model.*;
import org.example.hibernatecaching.model.compositekey.OrderCompositKeyWithId;
import org.example.hibernatecaching.repository.OrderRepository;
import org.example.hibernatecaching.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;

    public ResponseEntity<Product> save(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        Product saved = productRepository.save(product);
        return ResponseEntity.ok(saved);
    }

    public ResponseEntity<List<ProductResponse>> getAll() {
        List<Product> products = productRepository.findAll();
        List<ProductResponse> productResponses = new ArrayList<>();
        products.forEach(product -> {
            ProductResponse productResponse = new ProductResponse();
            productResponse.setName(product.getName());
            productResponse.setDescription(product.getDescription());
            productResponse.setPrice(product.getPrice());
            productResponse.setOrders(saveOrderToProductResponse(product.getOrders()));
            productResponses.add(productResponse);

        });
        return ResponseEntity.ok(productResponses);
    }

    private List<OrderResponse> saveOrderToProductResponse(List<Order> orders) {
        List<OrderResponse> orderResponses = new ArrayList<>();
        orders.forEach(order -> {
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setProductName(order.getProductName());
            orderResponse.setOrderDate(order.getOrderDate());
//            orderResponse.setUserName(order.getUser().getUserName());
            orderResponse.setQuantity(order.getQuantity());
            orderResponse.setPrice(order.getPrice());
            orderResponses.add(orderResponse);
        });
        return orderResponses;
    }

    public ResponseEntity<Product> getById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<Product> update(Long id, String productName) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
//
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        product.setName(productName);
        productRepository.save(product);
        return ResponseEntity.ok(product);
    }

    public ResponseEntity<String> delete(Long id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productRepository.deleteById(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

    public ResponseEntity<String> addOrderToProduct(Long id, Long orderId, LocalDateTime orderDate) {
        productRepository.findById(id).ifPresent(product -> {

            OrderCompositKeyWithId orderCompositKeyWithId = new OrderCompositKeyWithId();
            orderCompositKeyWithId.setOrderId(orderId);
            orderCompositKeyWithId.setOrderDate(orderDate);
            orderRepository.findById(orderCompositKeyWithId).ifPresent(order -> {
                product.addOrder(order);
                productRepository.save(product);
            });

        });
        return ResponseEntity.ok("Order added to product successfully");
    }
}

