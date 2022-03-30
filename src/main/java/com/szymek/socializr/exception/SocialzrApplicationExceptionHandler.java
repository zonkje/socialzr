package com.szymek.socializr.exception;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class SocialzrApplicationExceptionHandler {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
            .withZone(ZoneId.systemDefault());

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApplicationExceptionResponse> handleException(ResourceNotFoundException exception) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(ApplicationExceptionResponse.builder()
                .messages(List.of(exception.getMessage()))
                .httpStatus(status)
                .httpStatusCode(status.value())
                .timeStamp(formatter.format(Instant.now()))
                .build(), status);
    }

    //TODO -fix unnecessary code repetition
   /* private ApplicationResponse buildApplicationExceptionResponse(RuntimeException exception, HttpStatus status){
        return ApplicationExceptionResponse.builder()
                .messages(List.of(exception.getMessage()))
                .httpStatus(status)
                .httpStatusCode(status.value())
                .timeStamp(formatter.format(Instant.now()))
                .build();
    }*/

    @ExceptionHandler(UserAlreadyInGroupException.class)
    public ResponseEntity<ApplicationExceptionResponse> handleException(UserAlreadyInGroupException exception) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(ApplicationExceptionResponse.builder()
                .messages(List.of(exception.getMessage()))
                .httpStatus(status)
                .httpStatusCode(status.value())
                .timeStamp(formatter.format(Instant.now()))
                .build(), status);
    }

    @ExceptionHandler(ThumbUpException.class)
    public ResponseEntity<ApplicationExceptionResponse> handleException(ThumbUpException exception) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(ApplicationExceptionResponse.builder()
                .messages(List.of(exception.getMessage()))
                .httpStatus(status)
                .httpStatusCode(status.value())
                .timeStamp(formatter.format(Instant.now()))
                .build(), status);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApplicationExceptionResponse> handleException(UnauthorizedException exception) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        return new ResponseEntity<>(ApplicationExceptionResponse.builder()
                .messages(List.of(exception.getMessage()))
                .httpStatus(status)
                .httpStatusCode(status.value())
                .timeStamp(formatter.format(Instant.now()))
                .build(), status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApplicationExceptionResponse> handleException(MethodArgumentNotValidException exception) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(ApplicationExceptionResponse.builder()
                .messages(exception.getBindingResult()
                        .getFieldErrors()
                        .stream().map(
                                error -> error.getField() + ": " + error.getDefaultMessage()
                        ).collect(Collectors.toList()))
                .httpStatus(status)
                .httpStatusCode(status.value())
                .timeStamp(formatter.format(Instant.now()))
                .build(), status
        );

    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApplicationExceptionResponse> handleException(ConstraintViolationException exception) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(ApplicationExceptionResponse.builder()
                .messages(List.of(exception
                        .getMessage()
                        .substring(
                                exception.getMessage().indexOf(".") + 1
                        )))
                .httpStatus(status)
                .httpStatusCode(status.value())
                .timeStamp(formatter.format(Instant.now()))
                .build(), status);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ApplicationExceptionResponse> handleException(HttpMessageNotReadableException exception) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message;
        if(exception.getCause() instanceof UnrecognizedPropertyException) {
            UnrecognizedPropertyException unrecognizedPropertyException = (UnrecognizedPropertyException) exception.getCause();
            message = String.format("Unrecognized field: '%s'", unrecognizedPropertyException.getPropertyName());
        } else {
            message = exception.getHttpInputMessage().toString();
        }
        return new ResponseEntity<>(ApplicationExceptionResponse.builder()
                .messages(List.of(
                        message
                ))
                .httpStatus(status)
                .httpStatusCode(status.value())
                .timeStamp(formatter.format(Instant.now()))
                .build(), status);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<ApplicationExceptionResponse> handleException(Exception exception, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(ApplicationExceptionResponse.builder()
                .messages(List.of(
                        "Unexpected error occurred",
                        exception.getMessage()
                ))
                .httpStatus(status)
                .httpStatusCode(status.value())
                .timeStamp(formatter.format(Instant.now()))
                .build(), status);
    }

}
