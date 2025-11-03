package com.daniel.shortener.dto;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.NotBlank;

public record ShortUrlCreateRequest(

    @NotBlank @URL
    String destination

) {}