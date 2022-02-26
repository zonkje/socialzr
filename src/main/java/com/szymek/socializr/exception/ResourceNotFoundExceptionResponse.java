package com.szymek.socializr.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class ResourceNotFoundExceptionResponse {

    private final String message;
    private final HttpStatus httpStatus;
    private final int httpStatusCode;
    private final ZonedDateTime timestamp;

}
