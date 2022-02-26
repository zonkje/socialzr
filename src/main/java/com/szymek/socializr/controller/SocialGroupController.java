package com.szymek.socializr.controller;

import com.szymek.socializr.dto.SocialGroupDTO;
import com.szymek.socializr.service.SocialGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/social_group")
public class SocialGroupController {

    private final SocialGroupService socialGroupService;


    @GetMapping("/{socialGroupId}")
    public ResponseEntity<SocialGroupDTO> getSocialGroup(@PathVariable("socialGroupId") Long socialGroupId){
        SocialGroupDTO socialGroupDTO = socialGroupService.findById(socialGroupId);

        return new ResponseEntity<>(socialGroupDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Collection<SocialGroupDTO>> getAllSocialGroups(){
        Collection<SocialGroupDTO> socialGroupDTOS = socialGroupService.findAll();
        return new ResponseEntity<>(socialGroupDTOS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SocialGroupDTO> createSocialGroup(@RequestBody SocialGroupDTO socialGroupDTO){
        SocialGroupDTO createdSocialGroupDTO = socialGroupService.create(socialGroupDTO);

        return new ResponseEntity<>(createdSocialGroupDTO, HttpStatus.CREATED);
    }

}
