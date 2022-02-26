package com.szymek.socializr.controller;

import com.szymek.socializr.dto.PostDTO;
import com.szymek.socializr.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Collection;

@Validated
@RestController
@RequestMapping(path = "/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> getPost(@PathVariable("postId") @Min(1) Long postId) {
        PostDTO postDTO = postService.findById(postId);
        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Collection<PostDTO>> getAllPosts() {
        Collection<PostDTO> postDTOS = postService.findAll();
        return new ResponseEntity<>(postDTOS, HttpStatus.OK);
    }

    //TODO -add URL to response
    @PostMapping
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO) {
        PostDTO createdPost = postService.create(postDTO);

        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }
}
