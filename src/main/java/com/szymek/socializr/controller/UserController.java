package com.szymek.socializr.controller;

import com.szymek.socializr.common.ApplicationResponse;
import com.szymek.socializr.common.SocialzrConstants;
import com.szymek.socializr.dto.UserDTO;
import com.szymek.socializr.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Collection;

@Validated
@RestController
@RequestMapping(path = "/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("userId") @Min(1) Long userId) {
        UserDTO userDTO = userService.findById(userId);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Collection<UserDTO>> getAllUsers(
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_NUMBER, value = "page", required = false) @Min(0) Integer page,
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_SIZE, value = "size", required = false) @Min(0) Integer size
    ) {
        Collection<UserDTO> userDTOS = userService.findAll(page, size);
        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.create(userDTO);

        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    //TODO: -update, delete
    @PatchMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO,
                                              @PathVariable("userId") @Min(1) Long userId) {
        UserDTO updatedUser = userService.update(userDTO, userId);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApplicationResponse> deleteUser(@PathVariable("userId") @Min(1) Long userId) {
        ApplicationResponse response = userService.deleteById(userId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{userId}/join_group/{socialGroupId}")
    public ResponseEntity<ApplicationResponse> joinSocialGroup(@PathVariable("userId") @Min(1) Long userId,
                                                               @PathVariable("socialGroupId") @Min(1) Long socialGroupId) {
        ApplicationResponse response = userService.joinGroup(userId, socialGroupId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{userId}/leave_group/{socialGroupId}")
    public ResponseEntity<ApplicationResponse> leaveSocialGroup(@PathVariable("userId") @Min(1) Long userId,
                                                                @PathVariable("socialGroupId") @Min(1) Long socialGroupId) {
        ApplicationResponse response = userService.leaveGroup(userId, socialGroupId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
