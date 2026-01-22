package com.daniel.shortener.core.exceptions;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class BaseException extends RuntimeException {
    
    public record ExceptionResponse(
        int status,
        String error,
        String path,
        LocalDateTime timestamp
    ) {}

    private final LocalDateTime timestamp = LocalDateTime.now();

    private String message;
    private int status;

    public ExceptionResponse toResponse(String path) {
        ExceptionResponse response = new ExceptionResponse(status, message, path, timestamp);
        return response;
    }
}
