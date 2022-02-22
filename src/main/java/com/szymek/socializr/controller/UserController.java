package com.szymek.socializr.controller;

import com.szymek.socializr.model.User;
import com.szymek.socializr.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable("userId") Long userId) {
        return new ResponseEntity(userService.findById(userId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody User user) {
        User savedUser = userService.create(user);

        return ResponseEntity
                .created(
                        UriComponentsBuilder
                                .fromHttpUrl("http://localhost:8080/user/" + savedUser.getId().toString()
                                )
                                .build()
                                .toUri()
                )
                .build();
    }

}
