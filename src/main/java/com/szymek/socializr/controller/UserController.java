package com.szymek.socializr.controller;

import com.szymek.socializr.common.ApplicationResponse;
import com.szymek.socializr.common.SocialzrConstants;
import com.szymek.socializr.dto.UserDTO;
import com.szymek.socializr.service.UserService;
import com.szymek.socializr.validation.ValidId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.security.Principal;
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
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_NUMBER, value = "page", required = false)
            @Min(0) Integer page,
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_SIZE, value = "size", required = false)
            @Min(0) Integer size) {
        Collection<UserDTO> userDTOS = userService.findAll(page, size);
        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/social_group/{socialGroupId}")
    public ResponseEntity<Collection<UserDTO>> getAllUsersBySocialGroupId(
            @PathVariable("socialGroupId") @Min(1) @ValidId(entity = "SocialGroup") Long socialGroupId,
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_NUMBER, value = "page", required = false)
            @Min(0) Integer page,
            @RequestParam(defaultValue = SocialzrConstants.DEFAULT_PAGE_SIZE, value = "size", required = false)
            @Min(0) Integer size,
            Principal principal) {
        Collection<UserDTO> userDTOS = userService.findAllBySocialGroupId(socialGroupId, principal.getName(), page, size);
        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }

    /*
    TODO: -doesn't work
        throws AbstractMessageConverterMethodArgumentResolver$EmptyBodyCheckingHttpInputMessage
    */
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping
    public ResponseEntity<UserDTO> updateUser(
            @Valid @RequestBody UserDTO userDTO,
            Principal principal) {
        UserDTO updatedUser = userService.update(userDTO, principal.getName());
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/join_group/{socialGroupId}")
    public ResponseEntity<ApplicationResponse> joinSocialGroup(
            @PathVariable("socialGroupId") @Min(1) @ValidId(entity = "SocialGroup") Long socialGroupId,
            Principal principal) {
        ApplicationResponse response = userService.joinGroup(principal.getName(), socialGroupId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/leave_group/{socialGroupId}")
    public ResponseEntity<ApplicationResponse> leaveSocialGroup(
            @PathVariable("socialGroupId") @Min(1) @ValidId(entity = "SocialGroup") Long socialGroupId,
            Principal principal) {
        ApplicationResponse response = userService.leaveGroup(principal.getName(), socialGroupId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApplicationResponse> deleteUser(
            @PathVariable("userId") @Min(1) @ValidId(entity = "User") Long userId) {
        ApplicationResponse response = userService.deleteById(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
