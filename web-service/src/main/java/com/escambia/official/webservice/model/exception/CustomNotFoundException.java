package com.escambia.official.webservice.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CustomNotFoundException extends ResponseStatusException {
    public CustomNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

    public CustomNotFoundException() {
        super(HttpStatus.NOT_FOUND, "找不到您請求的資料，請重試");
    }
}
