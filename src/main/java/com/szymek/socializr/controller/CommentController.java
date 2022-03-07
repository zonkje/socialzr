package com.szymek.socializr.controller;

import com.szymek.socializr.dto.CommentDTO;
import com.szymek.socializr.service.CommentService;
import com.szymek.socializr.util.SocialzrConstants;
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
@RequiredArgsConstructor
@RequestMapping(path = "/comment")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDTO> getComment(@PathVariable("commentId") @Min(1) Long commentId) {
        CommentDTO commentDTO = commentService.findById(commentId);

        return new ResponseEntity<>(commentDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CommentDTO> createComment(@Valid @RequestBody CommentDTO commentDTO) {
        CommentDTO createdComment = commentService.create(commentDTO);

        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    //TODO: -this endpoint will be for getting all comments from logged user (after adding security)
    @GetMapping
    public ResponseEntity<Collection<CommentDTO>> getAllComments(
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_NUMBER, value = "page", required = false) @Min(0) Integer page,
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_SIZE, value = "size", required = false) @Min(0) Integer size
    ) {
        Collection<CommentDTO> commentDTOS = commentService.findAll(page, size);
        return new ResponseEntity<>(commentDTOS, HttpStatus.OK);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@Valid @RequestBody CommentDTO commentDTO,
                                                    @PathVariable("commentId") @Min(1) Long commentId) {
        CommentDTO updatedComment = commentService.update(commentDTO, commentId);

        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable("commentId") @Min(1) Long commentId){
        commentService.deleteById(commentId);
    }

}
