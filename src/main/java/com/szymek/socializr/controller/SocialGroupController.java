package com.szymek.socializr.controller;

import com.szymek.socializr.common.ApplicationResponse;
import com.szymek.socializr.common.SocialzrConstants;
import com.szymek.socializr.dto.SocialGroupDTO;
import com.szymek.socializr.dto.UserDTO;
import com.szymek.socializr.service.SocialGroupService;
import com.szymek.socializr.validation.ValidId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Collection;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/social_group")
public class SocialGroupController {

    private final SocialGroupService socialGroupService;

    @GetMapping("/{socialGroupId}")
    public ResponseEntity<SocialGroupDTO> getSocialGroup(
            @PathVariable("socialGroupId") @Min(1) @ValidId(entity = "SocialGroup") Long socialGroupId) {
        SocialGroupDTO socialGroupDTO = socialGroupService.findById(socialGroupId);

        return new ResponseEntity<>(socialGroupDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Collection<SocialGroupDTO>> getAllSocialGroups(
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_NUMBER, value = "page", required = false) @Min(0) Integer page,
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_SIZE, value = "size", required = false) @Min(0) Integer size
    ) {
        Collection<SocialGroupDTO> socialGroupDTOS = socialGroupService.findAll(page, size);
        return new ResponseEntity<>(socialGroupDTOS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SocialGroupDTO> createSocialGroup(@Valid @RequestBody SocialGroupDTO socialGroupDTO) {
        SocialGroupDTO createdSocialGroupDTO = socialGroupService.create(socialGroupDTO);

        return new ResponseEntity<>(createdSocialGroupDTO, HttpStatus.CREATED);
    }

    @PatchMapping("/{socialGroupId}")
    public ResponseEntity<SocialGroupDTO> updateSocialGroup(
            @Valid @RequestBody SocialGroupDTO socialGroupDTO,
            @PathVariable("socialGroupId") @Min(1) @ValidId(entity = "SocialGroup") Long socialGroupId) {
        SocialGroupDTO updatedSocialGroup = socialGroupService.update(socialGroupDTO, socialGroupId);

        return new ResponseEntity<>(updatedSocialGroup, HttpStatus.OK);
    }

    @DeleteMapping("/{socialGroupId}")
    public ResponseEntity<ApplicationResponse> deleteSocialGroup(
            @PathVariable("socialGroupId") @Min(1) @ValidId(entity = "SocialGroup") Long socialGroupId) {
        ApplicationResponse response = socialGroupService.deleteById(socialGroupId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/members/{socialGroupId}")
    public ResponseEntity<Collection<UserDTO>> getAllSocialGroupMembers(
            @PathVariable("socialGroupId") @Min(1) @ValidId(entity = "SocialGroup") Long socialGroupId,
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_NUMBER, value = "page", required = false) @Min(0) Integer page,
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_SIZE, value = "size", required = false) @Min(0) Integer size
    ) {
        Collection<UserDTO> socialGroupMembers = socialGroupService.findAllMembers(socialGroupId, page, size);
        return new ResponseEntity<>(socialGroupMembers, HttpStatus.OK);
    }

}
