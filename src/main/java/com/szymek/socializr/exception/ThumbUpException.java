package com.szymek.socializr.exception;

public class ThumbUpException extends RuntimeException {

    public ThumbUpException(Long widgetId, Long userId) {
        super(String.format("User with ID: %s has already given a thumb up to widget with ID: %s", userId, widgetId));
    }
}
