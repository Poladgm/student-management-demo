package com.management.student.app.exception;

import com.management.student.app.dto.ApiError;
import org.springframework.http.HttpStatus;


public class RestException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final ApiError apiError;

    RestException(HttpStatus httpStatus, ApiError apiError) {
        this.httpStatus = httpStatus;
        this.apiError = apiError;
    }

    public RestException(HttpStatus httpStatus, String errorCode, String errorDescription) {
        this(httpStatus, new ApiError (errorCode, errorDescription));
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
    public ApiError getApiError() {
        return this.apiError;
    }
}
