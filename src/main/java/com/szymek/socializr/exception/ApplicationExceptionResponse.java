package com.szymek.socializr.exception;

import com.szymek.socializr.common.ApplicationResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@SuperBuilder
public class ApplicationExceptionResponse extends ApplicationResponse {

    private final HttpStatus httpStatus;
    private final int httpStatusCode;

}
