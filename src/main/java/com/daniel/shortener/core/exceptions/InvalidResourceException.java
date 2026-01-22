package com.daniel.shortener.core.exceptions;

public final class InvalidResourceException extends BaseException {

    private static final int DEFAULT_HTTP_STATUS = 400;

    public InvalidResourceException(String message) {
        super(message, DEFAULT_HTTP_STATUS);
    }
    
}
