package com.esprit.tn.forum.controller;

import com.esprit.tn.forum.dto.PostRequest;
import com.esprit.tn.forum.dto.PostResponse;
import com.esprit.tn.forum.service.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/posts/")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    @ApiOperation("createPost")
    public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest) {
        postService.save(postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return status(HttpStatus.OK).body(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        return status(HttpStatus.OK).body(postService.getPost(id));
    }

    @GetMapping("by-topic/{id}")
    public ResponseEntity<List<PostResponse>> getPostsByTopic(Long id) {
        return status(HttpStatus.OK).body(postService.getPostsByTopic(id));
    }

    @GetMapping("by-user/{name}")
    public ResponseEntity<List<PostResponse>> getPostsByUsername(String username) {
        return status(HttpStatus.OK).body(postService.getPostsByUsername(username));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable("id") Long postId) {
//        try {
        boolean deleted = postService.deletePost(postId);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PutMapping("archive/{id}")
    public ResponseEntity<?> archivePost(@PathVariable("id") Long postId) {
        postService.archivePost(postId);
        return ResponseEntity.ok().build();
    }
}
