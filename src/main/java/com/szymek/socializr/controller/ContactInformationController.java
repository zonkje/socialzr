package com.szymek.socializr.controller;

import com.szymek.socializr.dto.ContactInformationDTO;
import com.szymek.socializr.service.ContactInformationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/contact_information")
public class ContactInformationController {

    private final ContactInformationService contactInformationService;

    @GetMapping("/{contactInformationId}")
    public ResponseEntity<ContactInformationDTO> getContactInformation(@PathVariable("contactInformationId") Long contactInformationId){
        ContactInformationDTO contactInformationDTO = contactInformationService.findById(contactInformationId);

        return new ResponseEntity<>(contactInformationDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ContactInformationDTO> createContactInformation(@RequestBody ContactInformationDTO contactInformationDTO){
        ContactInformationDTO createdContactInformation = contactInformationService.create(contactInformationDTO);

        return new ResponseEntity<>(createdContactInformation, HttpStatus.CREATED);
    }
}
