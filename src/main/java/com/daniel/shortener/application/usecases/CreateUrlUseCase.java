package com.daniel.shortener.application.usecases;

import com.daniel.shortener.core.entity.Slug;
import com.daniel.shortener.core.entity.URL;
import com.daniel.shortener.core.gateway.UrlGateway;
import com.daniel.shortener.core.generator.SlugGenerator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateUrlUseCase implements UseCase<String, URL> {

    private final SlugGenerator generator;
    private final UrlGateway gateway;

    @Override
    public URL execute(String originalUrl) {
        
        Slug slug;

        do {
            slug = generator.generate();
        } while (gateway.existsBySlug(slug.getValue()));

        URL url = new URL(slug, originalUrl);
        return gateway.save(url);
    }
    
}
