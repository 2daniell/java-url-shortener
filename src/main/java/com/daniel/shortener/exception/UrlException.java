package com.daniel.shortener.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

public abstract class UrlException extends RuntimeException {

    @JsonPropertyOrder({ "status", "error", "message", "path", "timestamp" })
    public record ExceptionResponse(
        int status,
        String error,
        String message,
        String path,
        LocalDateTime timestamp
    ) {}

    private HttpStatus status;
    private String error;
    private String message;
    private LocalDateTime timestamp;

    public UrlException(HttpStatus status, String message) {
        this.status = status;
        this.error = status.getReasonPhrase();
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public ExceptionResponse toResponse(String path) {
        ExceptionResponse response = new ExceptionResponse(
            status.value(), error, message, path, timestamp
        );
        return response;
    }

    public HttpStatus getHttpStatus() {
        return status;
    }
    
}
