package com.szymek.socializr.controller;

import com.szymek.socializr.exception.ResourceNotFoundException;
import com.szymek.socializr.model.ContactInformation;
import com.szymek.socializr.repository.ContactInformationRepository;
import com.szymek.socializr.service.ContactInformationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(path = "/contact_information")
public class ContactInformationController {

    private final ContactInformationService contactInformationService;

    public ContactInformationController(ContactInformationService contactInformationService) {
        this.contactInformationService = contactInformationService;
    }

    @GetMapping("/{contactInformationId}")
    public ResponseEntity<?> getContactInformation(@PathVariable("contactInformationId") Long contactInformationId){
        return contactInformationService
                .findById(contactInformationId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Contact Information", "ID", contactInformationId));
    }

    @PostMapping
    public ResponseEntity<?> createContactInformation(@RequestBody ContactInformation contactInformation){
        return ResponseEntity
                .created(UriComponentsBuilder
                        .fromHttpUrl("http://localhost:8080/contact_information/" +
                                contactInformationService.create(contactInformation).getId())
                        .build()
                        .toUri()
                )
                .body(contactInformation);
    }
}
