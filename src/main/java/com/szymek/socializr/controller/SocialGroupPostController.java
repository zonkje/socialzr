package com.szymek.socializr.controller;

import com.szymek.socializr.common.ApplicationResponse;
import com.szymek.socializr.common.SocialzrConstants;
import com.szymek.socializr.dto.PostThumbUpDTO;
import com.szymek.socializr.dto.SocialGroupPostDTO;
import com.szymek.socializr.service.SocialGroupPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Collection;

@Validated
@RestController
@RequestMapping(path = "/social_group_post")
@RequiredArgsConstructor
public class SocialGroupPostController {

    private final SocialGroupPostService socialGroupPostService;

    @GetMapping("/{postId}")
    public ResponseEntity<SocialGroupPostDTO> getPost(@PathVariable("postId") @Min(1) Long postId) {
        SocialGroupPostDTO socialGroupPostDTO = socialGroupPostService.findById(postId);
        return new ResponseEntity<>(socialGroupPostDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Collection<SocialGroupPostDTO>> getAllPosts(
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_NUMBER, value = "page", required = false) @Min(0) Integer page,
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_SIZE, value = "size", required = false) @Min(0) Integer size
    ) {
        Collection<SocialGroupPostDTO> socialGroupPostDTOS = socialGroupPostService.findAll(page, size);
        return new ResponseEntity<>(socialGroupPostDTOS, HttpStatus.OK);
    }

    @GetMapping("/label/id/{labelId}")
    public ResponseEntity<Collection<SocialGroupPostDTO>> getPostsByLabelId(
            @PathVariable("labelId") @Min(1) Long labelId,
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_NUMBER, value = "page", required = false) @Min(0) Integer page,
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_SIZE, value = "size", required = false) @Min(0) Integer size
    ) {
        Collection<SocialGroupPostDTO> socialGroupPostDTOS = socialGroupPostService.findAllByLabelId(labelId, page, size);
        return new ResponseEntity<>(socialGroupPostDTOS, HttpStatus.OK);
    }

    @GetMapping("/label/name/{labelName}")
    public ResponseEntity<Collection<SocialGroupPostDTO>> getPostsByLabelName(
            @PathVariable("labelName") @Size(min = 2) String labelName,
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_NUMBER, value = "page", required = false) @Min(0) Integer page,
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_SIZE, value = "size", required = false) @Min(0) Integer size
    ) {
        Collection<SocialGroupPostDTO> socialGroupPostDTOS = socialGroupPostService.findAllByLabelName(labelName, page, size);
        return new ResponseEntity<>(socialGroupPostDTOS, HttpStatus.OK);
    }

    @GetMapping("/social_group/{socialGroupId}")
    public ResponseEntity<Collection<SocialGroupPostDTO>> getPostsBySocialGroupId(
            @PathVariable("socialGroupId") @Min(1) Long socialGroupId,
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_NUMBER, value = "page", required = false) @Min(0) Integer page,
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_SIZE, value = "size", required = false) @Min(0) Integer size
    ) {
        Collection<SocialGroupPostDTO> socialGroupPostDTOS = socialGroupPostService.findAllBySocialGroupId(socialGroupId, page, size);
        return new ResponseEntity<>(socialGroupPostDTOS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SocialGroupPostDTO> createPost(@Valid @RequestBody SocialGroupPostDTO socialGroupPostDTO) {
        SocialGroupPostDTO createdSocialGroupPost = socialGroupPostService.create(socialGroupPostDTO);

        return new ResponseEntity<>(createdSocialGroupPost, HttpStatus.CREATED);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<SocialGroupPostDTO> updatePost(@Valid @RequestBody SocialGroupPostDTO socialGroupPostDTO,
                                                         @PathVariable("postId") @Min(1) Long postId) {
        SocialGroupPostDTO updatedSocialGroupPost = socialGroupPostService.update(socialGroupPostDTO, postId);

        return new ResponseEntity<>(updatedSocialGroupPost, HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApplicationResponse> deletePost(@PathVariable("postId") @Min(1) Long postId) {
        ApplicationResponse response = socialGroupPostService.deleteById(postId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/thumb_up")
    public ResponseEntity<PostThumbUpDTO> addPostThumbUp(@Valid @RequestBody PostThumbUpDTO postThumbUpDTO) {
        PostThumbUpDTO createdPostThumbUpDTO = socialGroupPostService.addThumbUpToPost(postThumbUpDTO);

        return new ResponseEntity<>(createdPostThumbUpDTO, HttpStatus.CREATED);
    }

    //TODO -change path to /thumb_up/{postId} when security will be configured
    @DeleteMapping("/thumb_up/{thumbUpId}")
    public ResponseEntity<ApplicationResponse> deletePostThumbUp(@PathVariable("thumbUpId") @Min(1) Long thumbUpId) {
        ApplicationResponse response = socialGroupPostService.deletePostThumbUpById(thumbUpId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
