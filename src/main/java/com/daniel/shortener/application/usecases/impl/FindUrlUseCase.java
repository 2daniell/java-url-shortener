package com.daniel.shortener.application.usecases.impl;

import com.daniel.shortener.application.usecases.UseCase;
import com.daniel.shortener.core.entities.ShortUrl;
import com.daniel.shortener.core.exceptions.NotFoundException;
import com.daniel.shortener.core.repositories.CacheRepository;
import com.daniel.shortener.core.repositories.Repository;

public final class FindUrlUseCase implements UseCase<String, ShortUrl> {

    private final CacheRepository<ShortUrl, String> cacheRepository;
    private final Repository<ShortUrl, String> repository;

    public FindUrlUseCase(CacheRepository<ShortUrl, String> cacheRepository, Repository<ShortUrl, String> repository) {
        this.cacheRepository = cacheRepository;
        this.repository = repository;
    }

    @Override
    public ShortUrl execute(String slug) {
        ShortUrl shortUrl = cacheRepository.getOrElse(slug, repository::find).orElseThrow(() -> new NotFoundException("Resource not found."));
        //ShortUrl shortUrl = repository.find(slug).orElseThrow(() -> new NotFoundException("Resource not found."));
        return shortUrl;
    }
    
}
