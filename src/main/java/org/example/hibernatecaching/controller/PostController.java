package org.example.hibernatecaching.controller;

import org.example.hibernatecaching.model.PostRequest;
import org.example.hibernatecaching.model.PostResponse;
import org.example.hibernatecaching.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody PostRequest postRequest) {
        return postService.save(postRequest);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<String> updatePost(@PathVariable Long postId,
                                             @RequestParam String content,
                                             @RequestParam String userName) {
        return postService.update(postId, content, userName);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        return postService.deletePost(postId);
    }
}

