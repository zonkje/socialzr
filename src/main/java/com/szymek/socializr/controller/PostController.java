package com.szymek.socializr.controller;

import com.szymek.socializr.exception.ResourceNotFoundException;
import com.szymek.socializr.model.Post;
import com.szymek.socializr.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;

@RestController
@RequestMapping(path = "/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/{postId}")
    public ResponseEntity<?> getPost(@PathVariable("postId") Long postId) {
        return postService
                .findById(postId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "ID", postId));
    }

    @GetMapping
    public ResponseEntity<?> getAllPosts() {
        return ResponseEntity.ok(postService.findAll());
    }

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody Post post) {
        return ResponseEntity
                .created(UriComponentsBuilder
                        .fromHttpUrl("http://localhost:8080/post/" +
                                postService.create(post).getId())
                        .build()
                        .toUri()
                )
                .body(post);
    }
}
