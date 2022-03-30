package com.szymek.socializr.exception;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String operation, String resourceName) {
        super(String.format("You don't have permission to %s this %s", operation, resourceName));
    }

}
