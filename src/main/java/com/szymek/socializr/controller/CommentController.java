package com.szymek.socializr.controller;

import com.szymek.socializr.dto.CommentDTO;
import com.szymek.socializr.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/comment")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDTO> getComment(@PathVariable("commentId") Long commentId){
        CommentDTO commentDTO = commentService.findById(commentId);

        return new ResponseEntity<>(commentDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Collection<CommentDTO>> getAllComments(){
        Collection<CommentDTO> commentDTOS = commentService.findAll();
        return new ResponseEntity<>(commentDTOS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?>  createComment(@RequestBody CommentDTO commentDTO){
        CommentDTO createdComment = commentService.create(commentDTO);

        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }
}
