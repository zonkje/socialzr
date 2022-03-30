package com.szymek.socializr.controller;

import com.szymek.socializr.dto.ContactInformationDTO;
import com.szymek.socializr.service.ContactInformationService;
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

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/contact_information")
public class ContactInformationController {

    private final ContactInformationService contactInformationService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{contactInformationId}")
    public ResponseEntity<ContactInformationDTO> getContactInformation(
            @PathVariable("contactInformationId") @Min(1) @ValidId(entity = "ContactInformation") Long contactInformationId) {
        ContactInformationDTO contactInformationDTO = contactInformationService.findById(contactInformationId);
        return new ResponseEntity<>(contactInformationDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<ContactInformationDTO> createContactInformation(
            @Valid @RequestBody ContactInformationDTO contactInformationDTO,
            Principal principal) {
        ContactInformationDTO createdContactInformation = contactInformationService
                .create(contactInformationDTO, principal.getName());
        return new ResponseEntity<>(createdContactInformation, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping
    public ResponseEntity<ContactInformationDTO> updateContactInformation(
            @Valid @RequestBody ContactInformationDTO contactInformationDTO,
            Principal principal
    ) {
        ContactInformationDTO updatedContactInformation = contactInformationService.update(contactInformationDTO,
                principal.getName());
        return new ResponseEntity<>(updatedContactInformation, HttpStatus.OK);
    }
}
