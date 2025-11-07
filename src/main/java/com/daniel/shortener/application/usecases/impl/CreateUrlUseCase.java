package com.daniel.shortener.application.usecases.impl;

import com.daniel.shortener.application.usecases.UseCase;
import com.daniel.shortener.core.entities.ShortUrl;
import com.daniel.shortener.core.entities.Slug;
import com.daniel.shortener.core.generators.SlugGenerator;
import com.daniel.shortener.core.repositories.Repository;

public final class CreateUrlUseCase implements UseCase<String, ShortUrl> {
    
    private final SlugGenerator generator;
    private Repository<ShortUrl, String> repository;

    public CreateUrlUseCase(SlugGenerator generator, Repository<ShortUrl, String> repository) {
        this.generator = generator;
        this.repository = repository;
    }

    @Override
    public ShortUrl execute(String destination) {
        
        Slug slug;

        do {
            slug = generator.generate();
        } while(repository.find(slug.getValue()).isPresent());

        ShortUrl shortUrl = new ShortUrl(slug, destination);
        return repository.save(shortUrl);
    }
    
}
