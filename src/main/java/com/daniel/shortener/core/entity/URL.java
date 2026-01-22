package com.daniel.shortener.core.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class URL {

    private Long id;
    private Slug slug;
    private String originalUrl;
    private LocalDateTime updatedAt;
    private final LocalDateTime createdAt;

    public URL(Slug slug, String originalUrl) {
        this(null, slug, originalUrl, LocalDateTime.now(), LocalDateTime.now());
    }
}
