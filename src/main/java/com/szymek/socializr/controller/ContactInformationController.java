package com.szymek.socializr.controller;

import com.szymek.socializr.model.ContactInformation;
import com.szymek.socializr.repository.ContactInformationRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/contact_information")
public class ContactInformationController {

    private final ContactInformationRepository contactInformationRepository;

    public ContactInformationController(ContactInformationRepository contactInformationRepository) {
        this.contactInformationRepository = contactInformationRepository;
    }

    @GetMapping("/{contactInformationId}")
    public ContactInformation getContactInformation(@PathVariable("contactInformationId") Long contactInformationId){
        return contactInformationRepository.findById(contactInformationId).get();
    }

    @PostMapping
    public ContactInformation createContactInformation(@RequestBody ContactInformation contactInformation){
        return contactInformationRepository.save(contactInformation);
    }
}
