package com.szymek.socializr.controller;

import com.szymek.socializr.dto.CommentDTO;
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

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO) {
        PostDTO createdPost = postService.create(postDTO);

        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO,
                                                    @PathVariable("postId") @Min(1) Long postId) {
        PostDTO updatedPost = postService.update(postDTO, postId);

        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable("postId") @Min(1) Long postId){
        postService.deleteById(postId);
    }

    @GetMapping("/comments/{postId}")
    public ResponseEntity<Collection<CommentDTO>> getAllCommentsByPost(@PathVariable("postId") @Min(1) Long postId) {
        Collection<CommentDTO> commentDTOS = postService.findAllPostComments(postId);
        return new ResponseEntity<>(commentDTOS, HttpStatus.OK);
    }

}
