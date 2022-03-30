package com.szymek.socializr.exception;

public class UsernameTakenException extends RuntimeException{

    public UsernameTakenException(String username) {
        super(String.format("Username must be unique. User with name: %s already exists. Try a different username.", username));
    }

}
