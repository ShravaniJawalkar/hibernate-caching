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

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity<String> createOrder(OrderRequest orderRequest) {
        // Logic to create an order
        Order order = new Order();
        order.setProductName(orderRequest.getProductName());
        order.setQuantity(orderRequest.getQuantity());
        order.setPrice(orderRequest.getPrice());
        order.setOrderDate(orderRequest.getOrderDate());
        orderRepository.save(order);
        return ResponseEntity.ok("Order created successfully");
    }

    public ResponseEntity<String> updateOrder(Long orderId, String orderDate, String orderName) {
        // Logic to update an order
        LocalDateTime now = LocalDateTime.parse(orderDate);
        OrderCompositKeyWithId orderCompositKeyWithId = new OrderCompositKeyWithId(orderId, now);
        Order order = orderRepository.findById(orderCompositKeyWithId).orElse(null);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        order.setProductName(orderName);
        orderRepository.save(order);
        return ResponseEntity.ok("Order updated successfully");
    }

    public ResponseEntity<OrderResponse> getOrderById(Long orderId, String orderDate) {
        // Logic to retrieve an order by ID
        LocalDateTime now = LocalDateTime.parse(orderDate);
        OrderCompositKeyWithId orderCompositKeyWithId = new OrderCompositKeyWithId(orderId, now);
        Order order = orderRepository.findById(orderCompositKeyWithId).orElse(null);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setProductName(order.getProductName());
        orderResponse.setQuantity(order.getQuantity());
        orderResponse.setPrice(order.getPrice());
        orderResponse.setOrderDate(order.getOrderDate());
        orderResponse.setUserName(order.getUser().getUserName());

        return ResponseEntity.ok(orderResponse);
    }

    public ResponseEntity<String> deleteOrder(Long orderId, String orderDate) {
        // Logic to delete an order
        LocalDateTime now = LocalDateTime.parse(orderDate);
        OrderCompositKeyWithId orderCompositKeyWithId = new OrderCompositKeyWithId(orderId, now);
        if (!orderRepository.existsById(orderCompositKeyWithId)) {
            return ResponseEntity.notFound().build();
        }
        orderRepository.deleteById(orderCompositKeyWithId);
        return ResponseEntity.ok("Order deleted successfully");
    }

    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        // Logic to retrieve all orders
        List<Order> orders = orderRepository.findAll();
        List<OrderResponse> orderResponses = new ArrayList<>();
        orders.forEach(order -> {
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setProductName(order.getProductName());
            orderResponse.setQuantity(order.getQuantity());
            orderResponse.setPrice(order.getPrice());
            orderResponse.setOrderDate(order.getOrderDate());
            orderResponse.setUserName(saveUserName(order.getUser()));
            orderResponse.setProducts(saveProducts(order.getProducts()));
            orderResponses.add(orderResponse);
        });
        return ResponseEntity.ok(orderResponses);
    }

    private String saveUserName(User user) {
        // Logic to convert User entity to String (e.g., user name)
        return user != null ? user.getUserName() : "Unknown User";
    }

    private List<ProductResponse> saveProducts(List<Product> products) {
        // Logic to convert Product entities to ProductResponse DTOs
        return products.stream().map(product -> {
            ProductResponse productResponse = new ProductResponse();
            productResponse.setName(product.getName());
            productResponse.setDescription(product.getDescription());
            productResponse.setPrice(product.getPrice());
            return productResponse;
        }).toList();
    }

    public ResponseEntity<String> updateOrderProduct(Long orderId, Long productId, String orderDate) {
        // Logic to update an order's product
        LocalDateTime now = LocalDateTime.parse(orderDate);
        OrderCompositKeyWithId orderCompositKeyWithId = new OrderCompositKeyWithId(orderId, now);
        Order order = orderRepository.findById(orderCompositKeyWithId).orElse(null);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        productRepository.findById(productId).ifPresent(order::addProduct);
        orderRepository.save(order);
        return ResponseEntity.ok("Order product updated successfully");

    }
}
