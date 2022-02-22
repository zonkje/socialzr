package com.szymek.socializr.controller;

import com.szymek.socializr.exception.ResourceNotFoundException;
import com.szymek.socializr.model.SocialGroup;
import com.szymek.socializr.repository.SocialGroupRepository;
import com.szymek.socializr.service.SocialGroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;

@RestController
@RequestMapping(path = "/social_group")
public class SocialGroupController {

    private final SocialGroupService socialGroupService;

    public SocialGroupController(SocialGroupService socialGroupService) {
        this.socialGroupService = socialGroupService;
    }

    @GetMapping("/{socialGroupId}")
    public ResponseEntity<?> getSocialGroup(@PathVariable("socialGroupId") Long socialGroupId){
        return socialGroupService
                .findById(socialGroupId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Social Group", "ID", socialGroupId));
    }

    @GetMapping
    public ResponseEntity<?> getAllSocialGroups(){
        return ResponseEntity.ok(socialGroupService.findAll());
    }

    @PostMapping
    public ResponseEntity<?> createSocialGroup(@RequestBody SocialGroup socialGroup){
        return ResponseEntity
                .created(UriComponentsBuilder
                        .fromHttpUrl("http://localhost:8080/socialGroup/" +
                                socialGroupService.create(socialGroup).getId())
                        .build()
                        .toUri()
                )
                .body(socialGroup);
    }

}
