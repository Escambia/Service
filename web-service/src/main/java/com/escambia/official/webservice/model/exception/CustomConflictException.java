package com.escambia.official.webservice.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CustomConflictException extends ResponseStatusException {
    public CustomConflictException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
