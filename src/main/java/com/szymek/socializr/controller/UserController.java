package com.szymek.socializr.controller;

import com.szymek.socializr.model.User;
import com.szymek.socializr.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable("userId") Long userId){
        return userRepository.findById(userId).get();
    }

    @GetMapping
    public Collection<User> getAllUsers(){
        return (Collection<User>) userRepository.findAll();
    }

    @PostMapping
    public User createUser(@RequestBody User user){
        return userRepository.save(user);
    }

}
