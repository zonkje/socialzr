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
import java.util.Collection;

@Validated
@RestController
@RequestMapping(path = "/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> getPost(@PathVariable("postId") @Min(1) @ValidId(entity = "Post") Long postId) {
        PostDTO postDTO = postService.findById(postId);
        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public ResponseEntity<Collection<PostDTO>> getAllPosts(
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_NUMBER, value = "page", required = false) @Min(0) Integer page,
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_SIZE, value = "size", required = false) @Min(0) Integer size
    ) {
        Collection<PostDTO> postDTOS = postService.findAll(page, size);
        return new ResponseEntity<>(postDTOS, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/label/id/{labelId}")
    public ResponseEntity<Collection<PostDTO>> getPostsByLabelId(
            @PathVariable("labelId") @Min(1) @ValidId(entity = "PostLabel") Long labelId,
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_NUMBER, value = "page", required = false) @Min(0) Integer page,
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_SIZE, value = "size", required = false) @Min(0) Integer size
    ) {
        Collection<PostDTO> postDTOS = postService.findAllByLabelId(labelId, page, size);
        return new ResponseEntity<>(postDTOS, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/label/name/{labelName}")
    public ResponseEntity<Collection<PostDTO>> getPostsByLabelName(
            @PathVariable("labelName") @Size(min = 2) String labelName,
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_NUMBER, value = "page", required = false) @Min(0) Integer page,
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_SIZE, value = "size", required = false) @Min(0) Integer size
    ) {
        Collection<PostDTO> postDTOS = postService.findAllByLabelName(labelName, page, size);
        return new ResponseEntity<>(postDTOS, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO) {
        PostDTO createdPost = postService.create(postDTO);

        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/{postId}")
    public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO,
                                              @PathVariable("postId") @Min(1) @ValidId(entity = "Post") Long postId) {
        PostDTO updatedPost = postService.update(postDTO, postId);

        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApplicationResponse> deletePost(@PathVariable("postId") @Min(1) @ValidId(entity = "Post") Long postId) {
        ApplicationResponse response = postService.deleteById(postId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/thumb_up")
    public ResponseEntity<PostThumbUpDTO> addPostThumbUp(@Valid @RequestBody PostThumbUpDTO postThumbUpDTO) {
        PostThumbUpDTO createdPostThumbUpDTO = postService.addThumbUpToPost(postThumbUpDTO);

        return new ResponseEntity<>(createdPostThumbUpDTO, HttpStatus.CREATED);
    }

    //TODO -change path to /thumb_up/{postId} when security will be configured
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/thumb_up/{thumbUpId}")
    public ResponseEntity<ApplicationResponse> deletePostThumbUp(
            @PathVariable("thumbUpId") @Min(1) @ValidId(entity = "PostThumbUp") Long thumbUpId
    ) {
        ApplicationResponse response = postService.deletePostThumbUpById(thumbUpId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
