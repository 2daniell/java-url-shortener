package com.daniel.shortener.infrastructure.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.daniel.shortener.core.exceptions.NotFoundException;
import com.daniel.shortener.core.exceptions.BaseException.ExceptionResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public final class GlobalExceptionHandler {
    
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFound(NotFoundException exception, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ExceptionResponse response = exception.toResponse(status.value(), request.getRequestURI());
        return ResponseEntity.status(status).body(response);
    }
    
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ExceptionResponse> handleNotFound(HttpRequestMethodNotSupportedException exception, HttpServletRequest request) {
        HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
        ExceptionResponse response = new ExceptionResponse(status.value(), exception.getMessage(), request.getRequestURI(), LocalDateTime.now());
        return ResponseEntity.status(status).body(response);
    }

}
