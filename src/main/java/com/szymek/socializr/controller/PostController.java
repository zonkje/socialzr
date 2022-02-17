package com.szymek.socializr.controller;

import com.szymek.socializr.model.Post;
import com.szymek.socializr.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{postId}")
    public Post getPost(@PathVariable("postId") Long postId){
        return postService.findById(postId);
    }

    @GetMapping
    public Collection<Post> getAllPosts(){
        return postService.findAll();
    }

    @PostMapping
    public Post createPost(@RequestBody Post post){
        return postService.create(post);
    }
}
