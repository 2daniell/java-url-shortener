package com.daniel.shortener.core.exceptions;

public final class NotFoundException extends BaseException {

    private static final int DEFAULT_HTTP_STATUS = 404;

    public NotFoundException(String message) {
        super(message, DEFAULT_HTTP_STATUS);
    }
    
}
