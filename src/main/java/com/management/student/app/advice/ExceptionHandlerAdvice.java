package com.management.student.app.advice;


import com.management.student.app.dto.ApiError;
import com.management.student.app.exception.RestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.nio.file.AccessDeniedException;
import java.util.Objects;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(RestException.class)
    public ResponseEntity<ApiError> handleRestException(RestException restException) {
        return ResponseEntity.status (restException.getHttpStatus ()).body (restException.getApiError ());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        ObjectError error = ex.getBindingResult ().getAllErrors ().stream ().findAny ().orElse (null);
        FieldError fieldError = (FieldError) error;
        String errorMessage = String.format ("%s", Objects.requireNonNull (fieldError).getDefaultMessage ());
        ApiError errorResponse = new ApiError (HttpStatus.BAD_REQUEST.name () , errorMessage);
        return ResponseEntity.status (HttpStatus.BAD_REQUEST).body (errorResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<ApiError> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        ApiError errorDetails = new ApiError (HttpStatus.FORBIDDEN.name (),"Insufficient Permission");
        return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> globalExceptionHandler(Exception ex, WebRequest request) {
        ApiError message = new ApiError (
                HttpStatus.INTERNAL_SERVER_ERROR.name (),
                ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
    }

}
