package com.escambia.official.webservice.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CustomUnprocessableEntityException extends ResponseStatusException {
    public CustomUnprocessableEntityException() {
        super(HttpStatus.UNPROCESSABLE_ENTITY, "傳送參數格式有誤，無法處理");
    }
    public CustomUnprocessableEntityException(String message) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, message);
    }
}
