package com.escambia.official.webservice.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CustomInternalServerErrorException extends ResponseStatusException {
    public CustomInternalServerErrorException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}
