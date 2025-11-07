package com.daniel.shortener.interfaces.rest.request;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.NotBlank;

public record CreateShortUrlRequest(
    @NotBlank @URL(message = "The destination must be a URL.")
    String destination
) {}
