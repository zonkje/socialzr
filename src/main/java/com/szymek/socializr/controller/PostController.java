package com.szymek.socializr.controller;

import com.szymek.socializr.common.ApplicationResponse;
import com.szymek.socializr.common.SocialzrConstants;
import com.szymek.socializr.dto.PostDTO;
import com.szymek.socializr.dto.PostThumbUpDTO;
import com.szymek.socializr.service.PostService;
import com.szymek.socializr.validation.ValidId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.security.Principal;
import java.util.Collection;

@Validated
@RestController
@RequestMapping(path = "/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> getPost(
            @PathVariable("postId") @Min(1) @ValidId(entity = "Post") Long postId) {
        PostDTO postDTO = postService.findById(postId);
        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public ResponseEntity<Collection<PostDTO>> getAllPosts(
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_NUMBER, value = "page", required = false)
            @Min(0) Integer page,
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_SIZE, value = "size", required = false)
            @Min(0) Integer size,
            @RequestParam(defaultValue = "0", value = "userId", required = false) @Min(0) Long userId) {
        Collection<PostDTO> postDTOS = userId != 0 ?
                postService.findAllByAuthor(userId, page, size) :
                postService.findAll(page, size);
        return new ResponseEntity<>(postDTOS, HttpStatus.OK);
    }

    //TODO: -might be deleted later
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/label/id/{labelId}")
    public ResponseEntity<Collection<PostDTO>> getPostsByLabelId(
            @PathVariable("labelId") @Min(1) @ValidId(entity = "PostLabel") Long labelId,
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_NUMBER, value = "page", required = false)
            @Min(0) Integer page,
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_SIZE, value = "size", required = false)
            @Min(0) Integer size) {
        Collection<PostDTO> postDTOS = postService.findAllByLabelId(labelId, page, size);
        return new ResponseEntity<>(postDTOS, HttpStatus.OK);
    }

    //TODO: -check if return only regular posts ('non-group' posts)
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/label/name/{labelName}")
    public ResponseEntity<Collection<PostDTO>> getPostsByLabelName(
            @PathVariable("labelName") @Size(min = 2) String labelName,
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_NUMBER, value = "page", required = false)
            @Min(0) Integer page,
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_SIZE, value = "size", required = false)
            @Min(0) Integer size) {
        Collection<PostDTO> postDTOS = postService.findAllByLabelName(labelName, page, size);
        return new ResponseEntity<>(postDTOS, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<PostDTO> addPost(
            @Valid @RequestBody PostDTO postDTO,
            Principal principal) {
        PostDTO createdPost = postService.create(postDTO, principal.getName());
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/thumb_up")
    public ResponseEntity<PostThumbUpDTO> addPostThumbUp(
            @Valid @RequestBody PostThumbUpDTO postThumbUpDTO,
            Principal principal) {
        PostThumbUpDTO createdPostThumbUpDTO = postService.addThumbUpToPost(postThumbUpDTO, principal.getName());
        return new ResponseEntity<>(createdPostThumbUpDTO, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping
    public ResponseEntity<PostDTO> updatePost(
            @Valid @RequestBody PostDTO postDTO,
            Principal principal) {
        PostDTO updatedPost = postService.update(postDTO, principal.getName());
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApplicationResponse> deletePost(
            @PathVariable("postId") @Min(1) @ValidId(entity = "Post") Long postId,
            Principal principal) {
        ApplicationResponse response = postService.deleteById(postId, principal.getName());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/thumb_up/{postId}")
    public ResponseEntity<ApplicationResponse> deletePostThumbUp(
            @PathVariable("postId") @Min(1) @ValidId(entity = "Post") Long postId,
            Principal principal) {
        ApplicationResponse response = postService.deletePostThumbUpByPostId(postId, principal.getName());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
