package com.udea.lab12026p.exception;

import java.time.LocalDateTime;
import java.util.Map;

public class ApiError {
    private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final Map<String, String> validations;

    public ApiError(LocalDateTime timestamp, int status, String error, String message, Map<String, String> validations) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.validations = validations;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, String> getValidations() {
        return validations;
    }
}
