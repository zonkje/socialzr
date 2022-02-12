package com.szymek.socializr.controller;

import com.szymek.socializr.model.SocialGroup;
import com.szymek.socializr.repository.SocialGroupRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/social_group")
public class SocialGroupController {

    private final SocialGroupRepository socialGroupRepository;

    public SocialGroupController(SocialGroupRepository socialGroupRepository) {
        this.socialGroupRepository = socialGroupRepository;
    }

    @GetMapping("/{socialGroupId}")
    public SocialGroup getSocialGroup(@PathVariable("socialGroupId") Long socialGroupId){
        return socialGroupRepository.findById(socialGroupId).get();
    }

    @GetMapping
    public Collection<SocialGroup> getAllSocialGroups(){
        return (Collection<SocialGroup>) socialGroupRepository.findAll();
    }

    @PostMapping
    public SocialGroup createSocialGroup(@RequestBody SocialGroup socialGroup){
        return socialGroupRepository.save(socialGroup);
    }

}
