package com.daniel.shortener.infrastructure.controller.response;

import java.time.LocalDateTime;

public record CreateUrlResponse(
    
    Long id,
    String original,
    String shortUrl,
    LocalDateTime createdAt

) {}
