package com.szymek.socializr.controller;

import com.szymek.socializr.model.SocialGroup;
import com.szymek.socializr.repository.SocialGroupRepository;
import com.szymek.socializr.service.SocialGroupService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/social_group")
public class SocialGroupController {

    private final SocialGroupService socialGroupService;

    public SocialGroupController(SocialGroupService socialGroupService) {
        this.socialGroupService = socialGroupService;
    }

    @GetMapping("/{socialGroupId}")
    public SocialGroup getSocialGroup(@PathVariable("socialGroupId") Long socialGroupId){
        return socialGroupService.findById(socialGroupId);
    }

    @GetMapping
    public Collection<SocialGroup> getAllSocialGroups(){
        return socialGroupService.findAll();
    }

    @PostMapping
    public SocialGroup createSocialGroup(@RequestBody SocialGroup socialGroup){
        return socialGroupService.create(socialGroup);
    }

}
