package com.daniel.shortener.dto;

import jakarta.validation.constraints.NotBlank;

public record ShortUrlCreateResponse(

    @NotBlank
    String slug

) {}