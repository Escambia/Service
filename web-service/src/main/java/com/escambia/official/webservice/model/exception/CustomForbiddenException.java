package com.escambia.official.webservice.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CustomForbiddenException extends ResponseStatusException {
    public CustomForbiddenException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}
