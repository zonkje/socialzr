package com.szymek.socializr.controller;

import com.szymek.socializr.common.ApplicationResponse;
import com.szymek.socializr.common.SocialzrConstants;
import com.szymek.socializr.dto.SocialGroupDTO;
import com.szymek.socializr.service.SocialGroupService;
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
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/social_group")
public class SocialGroupController {

    private final SocialGroupService socialGroupService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{socialGroupId}")
    public ResponseEntity<SocialGroupDTO> getSocialGroup(
            @PathVariable("socialGroupId") @Min(1) @ValidId(entity = "SocialGroup") Long socialGroupId) {
        SocialGroupDTO socialGroupDTO = socialGroupService.findById(socialGroupId);
        return new ResponseEntity<>(socialGroupDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public ResponseEntity<Collection<SocialGroupDTO>> getAllSocialGroups(
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_NUMBER, value = "page", required = false)
            @Min(0) Integer page,
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_SIZE, value = "size", required = false)
            @Min(0) Integer size) {
        Collection<SocialGroupDTO> socialGroupDTOS = socialGroupService.findAll(page, size);
        return new ResponseEntity<>(socialGroupDTOS, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<SocialGroupDTO> addSocialGroup(
            @Valid @RequestBody SocialGroupDTO socialGroupDTO,
            Principal principal) {
        SocialGroupDTO createdSocialGroupDTO = socialGroupService.create(socialGroupDTO, principal.getName());
        return new ResponseEntity<>(createdSocialGroupDTO, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping
    public ResponseEntity<SocialGroupDTO> updateSocialGroup(
            @Valid @RequestBody SocialGroupDTO socialGroupDTO,
            Principal principal) {
        SocialGroupDTO updatedSocialGroup = socialGroupService.update(socialGroupDTO, principal.getName());
        return new ResponseEntity<>(updatedSocialGroup, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{socialGroupId}")
    public ResponseEntity<ApplicationResponse> deleteSocialGroup(
            @PathVariable("socialGroupId") @Min(1) @ValidId(entity = "SocialGroup") Long socialGroupId,
            Principal principal) {
        ApplicationResponse response = socialGroupService.deleteById(socialGroupId, principal.getName());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}