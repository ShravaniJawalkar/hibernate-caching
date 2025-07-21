package org.example.hibernatecaching.controller;

import org.example.hibernatecaching.model.Order;
import org.example.hibernatecaching.model.OrderRequest;
import org.example.hibernatecaching.model.OrderResponse;
import org.example.hibernatecaching.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.createOrder(orderRequest);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<String> updateOrder(@PathVariable Long orderId, @RequestParam("order_date") String orderDate, @RequestParam("order_name") String orderName) {
        return orderService.updateOrder(orderId, orderDate, orderName);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long orderId, @RequestParam("order_date") String orderDate) {
        return orderService.getOrderById(orderId, orderDate);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long orderId, @RequestParam("order_date") String orderDate) {
        return orderService.deleteOrder(orderId, orderDate);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return orderService.getAllOrders();
    }
}
