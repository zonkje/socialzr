package com.szymek.socializr.controller;

import com.szymek.socializr.model.Comment;
import com.szymek.socializr.repository.CommentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/comment")
public class CommentController {

    private final CommentRepository commentRepository;

    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @GetMapping("/{postId}")
    public Comment getComment(@PathVariable("commentId") Long commentId){
        return commentRepository.findById(commentId).get();
    }

    @GetMapping
    public Collection<Comment> getAllComments(){
        return (Collection<Comment>) commentRepository.findAll();
    }

    @PostMapping
    public Comment createComment(@RequestBody Comment comment){
        return commentRepository.save(comment);
    }
}
