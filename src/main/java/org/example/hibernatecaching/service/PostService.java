package org.example.hibernatecaching.service;

import org.example.hibernatecaching.model.*;
import org.example.hibernatecaching.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    public ResponseEntity<String> save(PostRequest postRequest) {
        Post post = new Post();
        post.setContent(postRequest.getContent());
        post.setUser(saveUser(postRequest.getUser()));
        postRepository.save(post);
        post = postRepository.findById(post.getId()).orElse(null);
        String userName = (post != null && post.getUser() != null) ? post.getUser().getUserName() : "Unknown User";
        return ResponseEntity.ok("Post saved successfully with user: " + userName);
    }

    public ResponseEntity<String> update(Long postId, String content, String userName) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        post.setContent(content);
        post.getUser().setUserName(userName);
        postRepository.save(post);
        return ResponseEntity.ok("Post updated successfully");
    }

    public ResponseEntity<List<PostResponse>> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostResponse> postResponses = new ArrayList<>();
        for (Post post : posts) {
            PostResponse response = new PostResponse();
            response.setId(post.getId());
            response.setContent(post.getContent());
            if (post.getUser() != null) {
                response.setAuthor(post.getUser().getUserName());
                response.setEmail(post.getUser().getEmail());
            }
            postResponses.add(response);
        }
        return ResponseEntity.ok(postResponses);
    }

    public ResponseEntity<PostResponse> getPostById(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        PostResponse response = new PostResponse();
        response.setId(post.getId());
        response.setContent(post.getContent());
        if (post.getUser() != null) {
            response.setAuthor(post.getUser().getUserName());
            response.setEmail(post.getUser().getEmail());
        }
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<String> deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }
        postRepository.delete(post);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    private User saveUser(UserRequest userRequest) {
        User user = null;
        if (userService.isUserExists(userRequest.getUserName())) {
            user = userService.getUserByName(userRequest.getUserName());
        } else {
            user = new User();
            user.setUserName(userRequest.getUserName());
            user.setEmail(userRequest.getEmail());
            user.setPhone(userRequest.getPhone());
            user.setInsertedBy(userRequest.getCreatedBy());
            user.setAddress(saveAddress(userRequest.getAddress()));
            user.setOrders(saveOrders(userRequest.getOrders(), user));
        }

        return user;
    }

    private List<Order> saveOrders(List<OrderRequest> orders, User user) {
        List<Order> orderList = new ArrayList<>();
        for (OrderRequest orderRequest : orders) {
            Order order = new Order();
            order.setProductName(orderRequest.getProductName());
            order.setQuantity(orderRequest.getQuantity());
            order.setPrice(orderRequest.getPrice());
            order.setOrderDate(orderRequest.getOrderDate());
            order.setUser(user);
            orderList.add(order); // Assuming the order is saved in the repository
        }
        return orderList;
    }

    private Address saveAddress(AddressRequest address) {
        Address addr = new Address();
        addr.setStreet(address.getStreet());
        addr.setCity(address.getCity());
        addr.setState(address.getState());
        addr.setZipCode(address.getZipCode());
        return addr; // Assuming the address is saved in the repository
    }
}
