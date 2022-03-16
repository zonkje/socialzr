package com.szymek.socializr.controller;

import com.szymek.socializr.common.ApplicationResponse;
import com.szymek.socializr.dto.ViolationReportDTO;
import com.szymek.socializr.service.ViolationReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping(path = "/report")
@RequiredArgsConstructor
public class ViolationReportController {

    private final ViolationReportService violationReportService;

    @PostMapping
    public ResponseEntity<ApplicationResponse> reportUser(@Valid @RequestBody ViolationReportDTO violationReportDTO){
        ApplicationResponse response = violationReportService.reportUser(violationReportDTO);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
