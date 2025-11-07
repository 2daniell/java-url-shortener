package com.daniel.shortener.exception;

import org.springframework.http.HttpStatus;

public class UrlNotFoundException extends UrlException {

    public UrlNotFoundException(HttpStatus status, String message) {
        super(status, message);
    }
    
}
