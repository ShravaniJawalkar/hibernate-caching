package org.example.hibernatecaching.controller;

import org.example.hibernatecaching.model.*;
import org.example.hibernatecaching.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequest user) {
        return userService.createUser(user);

    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable String id, @RequestParam("updatedBy") String updatedBy, @RequestBody UserRequest user) {
        return userService.updateUser(id, updatedBy,user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return userService.getAllUsers();
    }

    @PatchMapping("/{id}/address")
    public ResponseEntity<String> patchUser(@PathVariable String id, @RequestParam("address") AddressRequest address) {
        return userService.updateAddress(id, address);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllUser() {
        return userService.deleteAllUser();
    }

    @GetMapping("/address")
    public ResponseEntity<List<Address>> getAllAddresses() {
        return userService.getAllAddress();
    }

}
