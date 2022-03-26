package com.szymek.socializr.controller;

import com.szymek.socializr.common.ApplicationResponse;
import com.szymek.socializr.common.SocialzrConstants;
import com.szymek.socializr.dto.ViolationReportDTO;
import com.szymek.socializr.service.ViolationReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Collection;

@Validated
@RestController
@RequestMapping(path = "/report")
@RequiredArgsConstructor
public class ViolationReportController {

    private final ViolationReportService violationReportService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public ResponseEntity<ApplicationResponse> reportUser(@Valid @RequestBody ViolationReportDTO violationReportDTO){
        ApplicationResponse response = violationReportService.reportUser(violationReportDTO);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<Collection<ViolationReportDTO>> getAllViolationReports(
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_NUMBER, value = "page", required = false) @Min(0) Integer page,
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_SIZE, value = "size", required = false) @Min(0) Integer size
    ) {
        Collection<ViolationReportDTO> violationReportDTOS = violationReportService.findAll(page, size);
        return new ResponseEntity<>(violationReportDTOS, HttpStatus.OK);
    }

}
