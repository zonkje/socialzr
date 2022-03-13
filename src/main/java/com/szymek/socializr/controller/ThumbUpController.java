package com.szymek.socializr.controller;

import com.szymek.socializr.common.ApplicationResponse;
import com.szymek.socializr.model.ThumbUp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/thumb_up")
public class ThumbUpController {

    @PostMapping
    public ResponseEntity<ApplicationResponse> addThumbUp(@Valid @RequestBody ThumbUp thumbUp) {

        return null;
    }

    @DeleteMapping("/{thumbUpId}")
    public ResponseEntity<ApplicationResponse> deleteThumbUp(@PathVariable("thumbUpId") @Min(1) Long thumbUpId) {

        return null;
    }
}
