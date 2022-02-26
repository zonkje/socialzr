package com.szymek.socializr.controller;

import com.szymek.socializr.dto.UserDTO;
import com.szymek.socializr.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("userId") Long userId) {
        UserDTO userDTO = userService.findById(userId);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Collection<UserDTO>> getAllUsers() {
        Collection<UserDTO> userDTOS = userService.findAll();
        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.create(userDTO);

        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

}
