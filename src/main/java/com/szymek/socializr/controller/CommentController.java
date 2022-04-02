package com.szymek.socializr.controller;

import com.szymek.socializr.common.ApplicationResponse;
import com.szymek.socializr.common.SocialzrConstants;
import com.szymek.socializr.dto.CommentDTO;
import com.szymek.socializr.dto.CommentThumbUpDTO;
import com.szymek.socializr.service.CommentService;
import com.szymek.socializr.validation.ValidId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.security.Principal;
import java.util.Collection;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/comment")
public class CommentController {

    private final CommentService commentService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDTO> getComment(
            @PathVariable("commentId") @Min(1) @ValidId(entity = "Comment") Long commentId) {
        CommentDTO commentDTO = commentService.findById(commentId);
        return new ResponseEntity<>(commentDTO, HttpStatus.OK);
    }

    //TODO: -add postman tests for this endpoint

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/my")
    public ResponseEntity<Collection<CommentDTO>> getAllUserComments(
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_NUMBER, value = "page", required = false)
            @Min(0) Integer page,
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_SIZE, value = "size", required = false)
            @Min(0) Integer size,
            Principal principal) {
        Collection<CommentDTO> commentDTOS = commentService.findAllByUser(principal.getName(), page, size);
        return new ResponseEntity<>(commentDTOS, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<Collection<CommentDTO>> getAllComments(
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_NUMBER, value = "page", required = false)
            @Min(0) Integer page,
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_SIZE, value = "size", required = false)
            @Min(0) Integer size) {
        Collection<CommentDTO> commentDTOS = commentService.findAll(page, size);
        return new ResponseEntity<>(commentDTOS, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/post/{postId}")
    public ResponseEntity<Collection<CommentDTO>> getAllCommentsByPost(
            @PathVariable("postId") @Min(1) @ValidId(entity = "Post") Long postId,
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_NUMBER, value = "page", required = false)
            @Min(0) Integer page,
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_SIZE, value = "size", required = false)
            @Min(0) Integer size) {
        Collection<CommentDTO> commentDTOS = commentService.findAllPostComments(postId, page, size);
        return new ResponseEntity<>(commentDTOS, HttpStatus.OK);
    }

    //TODO: -add validation for DTO's postId and userId
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<CommentDTO> addComment(
            @Valid @RequestBody CommentDTO commentDTO,
            Principal principal) {
        CommentDTO createdComment = commentService.create(commentDTO, principal.getName());
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/thumb_up")
    public ResponseEntity<CommentThumbUpDTO> addCommentThumbUp(
            @Valid @RequestBody CommentThumbUpDTO commentThumbUpDTO,
            Principal principal) {
        CommentThumbUpDTO createdCommentThumbUpDTO = commentService
                .addThumbUpToComment(commentThumbUpDTO, principal.getName());
        return new ResponseEntity<>(createdCommentThumbUpDTO, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping
    public ResponseEntity<CommentDTO> updateComment(
            @Valid @RequestBody CommentDTO commentDTO,
            Principal principal) {
        CommentDTO updatedComment = commentService.update(commentDTO, principal.getName());
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApplicationResponse> deleteComment(
            @PathVariable("commentId") @Min(1) @ValidId(entity = "Comment") Long commentId,
            Principal principal) {
        ApplicationResponse response = commentService.deleteById(commentId, principal.getName());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/thumb_up/{thumbUpId}")
    public ResponseEntity<ApplicationResponse> deleteCommentThumbUp(
            @PathVariable("thumbUpId") @Min(1) @ValidId(entity = "CommentThumbUp") Long thumbUpId,
            Principal principal) {
        ApplicationResponse response = commentService.deleteCommentThumbUpById(thumbUpId, principal.getName());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
