package com.szymek.socializr.exception.handler;

import com.szymek.socializr.exception.ResourceNotFoundException;
import com.szymek.socializr.exception.ResourceNotFoundExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestControllerAdvice
public class ResourceNotFoundExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleCommentNotFoundException(ResourceNotFoundException exception){
        return buildExceptionResponse(exception);
    }

    private ResponseEntity<Object> buildExceptionResponse(RuntimeException exception){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ResourceNotFoundExceptionResponse response = new ResourceNotFoundExceptionResponse(
                exception.getMessage(),
                httpStatus,
                httpStatus.value(),
                ZonedDateTime.now(ZoneId.systemDefault())
        );
        return new ResponseEntity<>(response, httpStatus);
    }

}
