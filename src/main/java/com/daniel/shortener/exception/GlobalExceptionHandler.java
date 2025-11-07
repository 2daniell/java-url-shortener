package com.daniel.shortener.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.daniel.shortener.exception.UrlException.ExceptionResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UrlNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFound(UrlNotFoundException exception, HttpServletRequest request) {
        ExceptionResponse response = exception.toResponse(request.getRequestURI());
        return ResponseEntity.status(exception.getHttpStatus()).body(response);
    }
    
}
