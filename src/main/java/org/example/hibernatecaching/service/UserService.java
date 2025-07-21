package org.example.hibernatecaching.service;

import org.example.hibernatecaching.model.*;
import org.example.hibernatecaching.repository.AddressRepository;
import org.example.hibernatecaching.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    public ResponseEntity<String> createUser(UserRequest userRequest) {
        // Logic to create a user
        // For demonstration, returning a placeholder response
        User user = new User();
        user.setUserName(userRequest.getUserName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        user.setAddress(saveAddress(userRequest.getAddress()));
        saveOrderRequestToOrder(userRequest.getOrders(), user);
        user.setInsertedBy(userRequest.getCreatedBy());
        userRepository.save(user);
        String name = userRepository.findById(user.getId()).map(User::getUserName).orElse(null);
        return ResponseEntity.ok("User created successfully" + " with name: " + name);
    }

    private void saveOrderRequestToOrder(List<OrderRequest> orders, User user) {
        if (orders != null && !orders.isEmpty()) {
            orders.forEach(orderRequest -> {
                Order order = new Order();
                order.setProductName(orderRequest.getProductName());
                order.setQuantity(orderRequest.getQuantity());
                order.setPrice(orderRequest.getPrice());
                order.setOrderDate(orderRequest.getOrderDate());
                user.addOrder(order);
            });
        }
    }

    private Address saveAddress(AddressRequest address) {
        // Logic to save address
        // For demonstration, returning a placeholder address
        Address addr = new Address();
        addr.setStreet(address.getStreet());
        addr.setCity(address.getCity());
        addr.setState(address.getState());
        addr.setZipCode(address.getZipCode());
        return addr; // Assuming the address is saved in the repository
    }

    public ResponseEntity<String> updateUser(String id, String updatedBy, UserRequest userRequest) {
        // Logic to update a user
        // For demonstration, returning a placeholder response
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        user.setUserName(userRequest.getUserName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        user.setUpdatedBy(updatedBy);
        userRepository.save(user);
        return ResponseEntity.ok("User updated successfully");
    }

    @CacheEvict(cacheNames = "userCache", key = "#id")
    public ResponseEntity<User> getUserById(String id) {
        // Logic to retrieve a user by ID
        // For demonstration, returning a placeholder response
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    public ResponseEntity<List<UserResponse>> getAllUsers() {
        // Logic to retrieve all users
        // For demonstration, returning a placeholder response
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        List<UserResponse> userResponses = new ArrayList<>();
        users.stream().forEach(user -> {
            UserResponse userResponse = new UserResponse();
            userResponse.setUserName(user.getUserName());
            userResponse.setEmail(user.getEmail());
            userResponse.setPhone(user.getPhone());
            userResponse.setAddress(saveResponseAddress(user.getAddress()));
            userResponse.setCreatedBy(user.getInsertedBy());
            userResponse.setOrders(setOrders(user.getOrders()));
            userResponses.add(userResponse);
        });
        return ResponseEntity.ok(userResponses);
    }

    private List<OrderRequest> setOrders(List<Order> orders) {
        List<OrderRequest> orderRequests = new ArrayList<>();
        if (orders != null && !orders.isEmpty()) {
            orders.forEach(order -> {
                OrderRequest orderRequest = new OrderRequest();
                orderRequest.setProductName(order.getProductName());
                orderRequest.setQuantity(order.getQuantity());
                orderRequest.setPrice(order.getPrice());
                orderRequest.setOrderDate(order.getOrderDate());
                orderRequests.add(orderRequest);
            });
        }
        return orderRequests; // Assuming the orders are converted correctly
    }

    private AddressRequest saveResponseAddress(Address address) {
        // Logic to convert Address to AddressRequest
        // For demonstration, returning a placeholder address request
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setStreet(address.getStreet());
        addressRequest.setCity(address.getCity());
        addressRequest.setState(address.getState());
        addressRequest.setZipCode(address.getZipCode());
        return addressRequest; // Assuming the address is converted correctly
    }

    public ResponseEntity<String> updateAddress(String id, AddressRequest address) {
        userRepository.findById(id).ifPresent(user -> {
            user.setAddress(saveAddress(address));
            userRepository.save(user);
        });
        return ResponseEntity.ok("Address updated successfully");
    }

    public ResponseEntity<String> deleteAllUser() {
        userRepository.deleteAll();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("All users deleted successfully");
    }

    public ResponseEntity<List<Address>> getAllAddress() {
        List<Address> addresses = addressRepository.findAll();
        return ResponseEntity.ok(addresses);
    }

    public ResponseEntity<String> deleteOrderByUserId(String id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        List<Order> orders = user.getOrders();
        if (orders != null && !orders.isEmpty()) {
            Order orderToRemove = orders.getFirst();
            orderToRemove.setUser(null);
            orders.remove(orderToRemove);
        }

        user.setOrders(orders);// Clear the orders associated with the user
        userRepository.save(user); // Save the user to persist changes
        return ResponseEntity.ok("Orders deleted successfully for user with ID: " + id);
    }

    public boolean isUserExists(String name) {
        return userRepository.existsByUserName(name);
    }
    public User getUserByName(String name) {
        return userRepository.findByUserName(name).orElse(null);
    }

    public ResponseEntity<String> updateOrderByUserId(String id, OrderRequest order) {
        userRepository.findById(id).ifPresent(user -> {
            Order orderEntity = new Order();
            orderEntity.setProductName(order.getProductName());
            orderEntity.setQuantity(order.getQuantity());
            orderEntity.setPrice(order.getPrice());
            orderEntity.setOrderDate(order.getOrderDate());
            orderEntity.setUser(user);
            user.addOrder(orderEntity);
            userRepository.save(user);
        });
        return ResponseEntity.ok("Order updated successfully for user with ID: " + id);
    }
}
