package com.szymek.socializr.controller;

import com.szymek.socializr.model.ContactInformation;
import com.szymek.socializr.repository.ContactInformationRepository;
import com.szymek.socializr.service.ContactInformationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/contact_information")
public class ContactInformationController {

    private final ContactInformationService contactInformationService;

    public ContactInformationController(ContactInformationService contactInformationService) {
        this.contactInformationService = contactInformationService;
    }

    @GetMapping("/{contactInformationId}")
    public ContactInformation getContactInformation(@PathVariable("contactInformationId") Long contactInformationId){
        return contactInformationService.findById(contactInformationId);
    }

    @PostMapping
    public ContactInformation createContactInformation(@RequestBody ContactInformation contactInformation){
        return contactInformationService.create(contactInformation);
    }
}
