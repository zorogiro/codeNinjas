package com.esprit.tn.forum.controller;

<<<<<<< Updated upstream
import com.esprit.tn.forum.dto.*;
import com.esprit.tn.forum.service.*;
import lombok.*;
=======
import com.esprit.tn.forum.dto.PostRequest;
import com.esprit.tn.forum.dto.PostResponse;
import com.esprit.tn.forum.service.PostService;
import lombok.AllArgsConstructor;
>>>>>>> Stashed changes
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
<<<<<<< Updated upstream
@RequestMapping("/api/posts")
=======
@RequestMapping("/api/posts/")
>>>>>>> Stashed changes
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest) {
        postService.save(postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

<<<<<<< Updated upstream
    @GetMapping({"/visible"})
    public ResponseEntity<List<PostResponse>> getAllVisiblePosts() {
        return status(HttpStatus.OK).body(postService.getAllVisiblePosts());
    }
    @GetMapping({"/unvisible"})
    public ResponseEntity<List<PostResponse>> getAllUnvisiblePosts() {
        return status(HttpStatus.OK).body(postService.getAllUnvisiblePosts());
=======
    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return status(HttpStatus.OK).body(postService.getAllPosts());
>>>>>>> Stashed changes
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        return status(HttpStatus.OK).body(postService.getPost(id));
    }

    @GetMapping("by-topic/{id}")
    public ResponseEntity<List<PostResponse>> getPostsByTopic(@PathVariable Long id) {
        return status(HttpStatus.OK).body(postService.getPostsByTopic(id));
    }

    @GetMapping("by-user/{name}")
    public ResponseEntity<List<PostResponse>> getPostsByUsername(String username) {
        return status(HttpStatus.OK).body(postService.getPostsByUsername(username));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable("id") Long postId) {
//        try {
<<<<<<< Updated upstream
            boolean deleted = postService.deletePost(postId);
            if (deleted) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
//        } catch (PostNotFoundException e) {
//            return ResponseEntity.notFound().build();
//        } catch (UnauthorizedException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
=======
        boolean deleted = postService.deletePost(postId);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PutMapping("/{id}/archive")
    public ResponseEntity<?> archivePost(@PathVariable("id") Long postId) {
        postService.archivePost(postId);
        return ResponseEntity.ok().build();
>>>>>>> Stashed changes
    }
}
