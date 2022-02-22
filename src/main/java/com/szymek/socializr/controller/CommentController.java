package com.szymek.socializr.controller;

import com.szymek.socializr.exception.ResourceNotFoundException;
import com.szymek.socializr.model.Comment;
import com.szymek.socializr.repository.CommentRepository;
import com.szymek.socializr.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;

@RestController
@RequestMapping(path = "/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<?> getComment(@PathVariable("commentId") Long commentId){
        return commentService
                .findById(commentId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "ID", commentId));
    }

    @GetMapping
    public ResponseEntity<?> getAllComments(){
        return ResponseEntity.ok(commentService.findAll());
    }

    @PostMapping
    public ResponseEntity<?>  createComment(@RequestBody Comment comment){
        return ResponseEntity
                .created(UriComponentsBuilder
                        .fromHttpUrl("http://localhost:8080/comment/" +
                                commentService.create(comment).getId())
                        .build()
                        .toUri()
                )
                .body(comment);
    }
}
