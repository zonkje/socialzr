package com.szymek.socializr.controller;

import com.szymek.socializr.model.Post;
import com.szymek.socializr.repository.PostRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/post")
public class PostController {

    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping("/{postId}")
    public Post getPost(@PathVariable("postId") Long postId){
        return postRepository.findById(postId).get();
    }

    @GetMapping
    public Collection<Post> getAllPosts(){
        return (Collection<Post>) postRepository.findAll();
    }

    @PostMapping
    public Post createPost(@RequestBody Post post){
        return postRepository.save(post);
    }
}
