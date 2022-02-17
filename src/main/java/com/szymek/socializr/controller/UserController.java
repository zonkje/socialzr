package com.szymek.socializr.controller;

import com.szymek.socializr.model.User;
import com.szymek.socializr.service.UserService;
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
    public User getUser(@PathVariable("userId") Long userId){
        return userService.findById(userId);
    }

    @GetMapping
    public Collection<User> getAllUsers(){
        return userService.findAll();
    }

    @PostMapping
    public User createUser(@RequestBody User user){
        return userService.create(user);
    }

}
