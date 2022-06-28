package com.szymek.socializr.controller;

import com.szymek.socializr.common.ApplicationResponse;
import com.szymek.socializr.common.SocialzrConstants;
import com.szymek.socializr.dto.PostThumbUpDTO;
import com.szymek.socializr.dto.SocialGroupPostDTO;
import com.szymek.socializr.service.SocialGroupPostService;
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
@RequestMapping(path = "/social_group_post")
@RequiredArgsConstructor
public class SocialGroupPostController {

    private final SocialGroupPostService socialGroupPostService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{socialGroupPostId}")
    public ResponseEntity<SocialGroupPostDTO> getSocialGroupPost(
            @PathVariable("socialGroupPostId") @Min(1) @ValidId(entity = "SocialGroupPost") Long socialGroupPostId) {
        SocialGroupPostDTO socialGroupPostDTO = socialGroupPostService.findById(socialGroupPostId);
        return new ResponseEntity<>(socialGroupPostDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<Collection<SocialGroupPostDTO>> getAllSocialGroupPosts(
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_NUMBER, value = "page", required = false)
            @Min(0) Integer page,
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_SIZE, value = "size", required = false)
            @Min(0) Integer size) {
        Collection<SocialGroupPostDTO> socialGroupPostDTOS = socialGroupPostService.findAll(page, size);
        return new ResponseEntity<>(socialGroupPostDTOS, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/label/id/{labelId}")
    public ResponseEntity<Collection<SocialGroupPostDTO>> getSocialGroupPostsByLabelId(
            @PathVariable("labelId") @Min(1) @ValidId(entity = "PostLabel") Long labelId,
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_NUMBER, value = "page", required = false)
            @Min(0) Integer page,
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_SIZE, value = "size", required = false)
            @Min(0) Integer size,
            Principal principal) {
        Collection<SocialGroupPostDTO> socialGroupPostDTOS = socialGroupPostService.findAllByLabelId(labelId,
                principal.getName(), page, size);
        return new ResponseEntity<>(socialGroupPostDTOS, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/label/name/{labelName}")
    public ResponseEntity<Collection<SocialGroupPostDTO>> getSocialGroupPostsByLabelName(
            @PathVariable("labelName") @Size(min = 2) String labelName,
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_NUMBER, value = "page", required = false)
            @Min(0) Integer page,
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_SIZE, value = "size", required = false)
            @Min(0) Integer size,
            Principal principal) {
        Collection<SocialGroupPostDTO> socialGroupPostDTOS = socialGroupPostService.findAllByLabelName(labelName,
                principal.getName(), page, size);
        return new ResponseEntity<>(socialGroupPostDTOS, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/social_group/{socialGroupId}")
    public ResponseEntity<Collection<SocialGroupPostDTO>> getSocialGroupPostsBySocialGroupId(
            @PathVariable("socialGroupId") @Min(1) @ValidId(entity = "SocialGroup") Long socialGroupId,
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_NUMBER, value = "page", required = false)
            @Min(0) Integer page,
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_SIZE, value = "size", required = false)
            @Min(0) Integer size,
            Principal principal) {
        Collection<SocialGroupPostDTO> socialGroupPostDTOS = socialGroupPostService
                .findAllBySocialGroupId(socialGroupId, principal.getName(), page, size);
        return new ResponseEntity<>(socialGroupPostDTOS, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<SocialGroupPostDTO> addSocialGroupPost(
            @Valid @RequestBody SocialGroupPostDTO socialGroupPostDTO,
            Principal principal) {
        SocialGroupPostDTO createdSocialGroupPost = socialGroupPostService.create(socialGroupPostDTO,
                principal.getName());
        return new ResponseEntity<>(createdSocialGroupPost, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/thumb_up")
    public ResponseEntity<PostThumbUpDTO> addSocialGroupPostThumbUp(
            @Valid @RequestBody PostThumbUpDTO postThumbUpDTO,
            Principal principal) {
        PostThumbUpDTO createdPostThumbUpDTO = socialGroupPostService.addThumbUpToPost(postThumbUpDTO,
                principal.getName());
        return new ResponseEntity<>(createdPostThumbUpDTO, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping
    public ResponseEntity<SocialGroupPostDTO> updateSocialGroupPost(
            @Valid @RequestBody SocialGroupPostDTO socialGroupPostDTO,
            Principal principal) {
        SocialGroupPostDTO updatedSocialGroupPost = socialGroupPostService.update(socialGroupPostDTO,
                principal.getName());
        return new ResponseEntity<>(updatedSocialGroupPost, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApplicationResponse> deleteSocialGroupPost(
            @PathVariable("postId") @Min(1) @ValidId(entity = "SocialGroupPost") Long postId,
            Principal principal) {
        ApplicationResponse response = socialGroupPostService.deleteById(postId, principal.getName());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/thumb_up/{thumbUpId}")
    public ResponseEntity<ApplicationResponse> deleteSocialGroupPostThumbUp(
            @PathVariable("thumbUpId") @Min(1) @ValidId(entity = "PostThumbUp") Long thumbUpId,
            Principal principal) {
        ApplicationResponse response = socialGroupPostService.deletePostThumbUpById(thumbUpId, principal.getName());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
