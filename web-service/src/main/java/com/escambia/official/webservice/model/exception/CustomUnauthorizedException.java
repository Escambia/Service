package com.escambia.official.webservice.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CustomUnauthorizedException extends ResponseStatusException {
    public CustomUnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
