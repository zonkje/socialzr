package com.szymek.socializr.controller;

import com.szymek.socializr.dto.UserDTO;
import com.szymek.socializr.security.SignUpRequest;
import com.szymek.socializr.service.UserService;
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
@RequiredArgsConstructor
@RequestMapping(path = "/sign_up")
public class AuthController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> signUpUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        UserDTO createdUser = userService.create(signUpRequest);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

}
