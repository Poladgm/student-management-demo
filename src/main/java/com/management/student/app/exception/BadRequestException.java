package com.management.student.app.exception;


import org.springframework.http.HttpStatus;

public class BadRequestException extends RestException {

    public BadRequestException(String errorCode, String errorDescription) {
        super(HttpStatus.NOT_FOUND, errorCode, errorDescription);
    }
}
