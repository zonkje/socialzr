package com.szymek.socializr.exception;

public class UserAlreadyInGroupException extends RuntimeException{

    public UserAlreadyInGroupException(Long userId, Long socialGroupId) {
        super(String.format("User with ID: %s is already a member of this group with ID: %s", userId, socialGroupId));
    }
}
