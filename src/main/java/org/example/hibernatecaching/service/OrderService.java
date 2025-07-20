package org.example.hibernatecaching.service;

import org.example.hibernatecaching.model.Order;
import org.example.hibernatecaching.model.OrderRequest;
import org.example.hibernatecaching.model.compositekey.OrderCompositKeyWithId;
import org.example.hibernatecaching.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

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

    public ResponseEntity<Order> getOrderById(Long orderId, String orderDate) {
        // Logic to retrieve an order by ID
        LocalDateTime now = LocalDateTime.parse(orderDate);
        OrderCompositKeyWithId orderCompositKeyWithId = new OrderCompositKeyWithId(orderId, now);
        Order order = orderRepository.findById(orderCompositKeyWithId).orElse(null);
        return ResponseEntity.ok(order);
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

    public ResponseEntity<List<Order>> getAllOrders() {
        // Logic to retrieve all orders
        List<Order> orders = orderRepository.findAll();
        return ResponseEntity.ok(orders);
    }
}
