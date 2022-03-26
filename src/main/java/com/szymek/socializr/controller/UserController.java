package com.szymek.socializr.controller;

import com.szymek.socializr.common.ApplicationResponse;
import com.szymek.socializr.common.SocialzrConstants;
import com.szymek.socializr.dto.UserDTO;
import com.szymek.socializr.service.UserService;
import com.szymek.socializr.validation.ValidId;
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
@RequestMapping(path = "/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("userId") @Min(1) @ValidId(entity = "User") Long userId) {
        UserDTO userDTO = userService.findById(userId);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public ResponseEntity<Collection<UserDTO>> getAllUsers(
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_NUMBER, value = "page", required = false) @Min(0) Integer page,
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_SIZE, value = "size", required = false) @Min(0) Integer size
    ) {
        Collection<UserDTO> userDTOS = userService.findAll(page, size);
        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(
            @Valid @RequestBody UserDTO userDTO,
            @PathVariable("userId") @Min(1) @ValidId(entity = "User") Long userId) {
        UserDTO updatedUser = userService.update(userDTO, userId);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApplicationResponse> deleteUser(
            @PathVariable("userId") @Min(1) @ValidId(entity = "User") Long userId) {
        ApplicationResponse response = userService.deleteById(userId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/{userId}/join_group/{socialGroupId}")
    public ResponseEntity<ApplicationResponse> joinSocialGroup(
            @PathVariable("userId") @Min(1) @ValidId(entity = "User") Long userId,
            @PathVariable("socialGroupId") @Min(1) @ValidId(entity = "SocialGroup") Long socialGroupId) {
        ApplicationResponse response = userService.joinGroup(userId, socialGroupId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/{userId}/leave_group/{socialGroupId}")
    public ResponseEntity<ApplicationResponse> leaveSocialGroup(
            @PathVariable("userId") @Min(1) @ValidId(entity = "User") Long userId,
            @PathVariable("socialGroupId") @Min(1) @ValidId(entity = "SocialGroup") Long socialGroupId) {
        ApplicationResponse response = userService.leaveGroup(userId, socialGroupId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
