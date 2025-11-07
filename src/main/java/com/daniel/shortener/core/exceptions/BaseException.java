package com.daniel.shortener.core.exceptions;

import java.time.LocalDateTime;

public abstract class BaseException extends RuntimeException {
    
    public record ExceptionResponse(
        int status,
        String message,
        String path,
        LocalDateTime timestamp
    ) {}

    private String message;
    private LocalDateTime timestamp;

    public BaseException(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public ExceptionResponse toResponse(int status, String path) {
        ExceptionResponse response = new ExceptionResponse(status, message, path, timestamp);
        return response;
    }
}
