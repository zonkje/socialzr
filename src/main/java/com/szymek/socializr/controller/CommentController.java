package com.szymek.socializr.controller;

import com.szymek.socializr.model.Comment;
import com.szymek.socializr.repository.CommentRepository;
import com.szymek.socializr.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{postId}")
    public Comment getComment(@PathVariable("commentId") Long commentId){
        return commentService.findById(commentId);
    }

    @GetMapping
    public Collection<Comment> getAllComments(){
        return commentService.findAll();
    }

    @PostMapping
    public Comment createComment(@RequestBody Comment comment){
        return commentService.create(comment);
    }
}
