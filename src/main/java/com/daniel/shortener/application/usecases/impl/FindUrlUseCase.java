package com.daniel.shortener.application.usecases.impl;

import java.util.concurrent.CompletableFuture;

import com.daniel.shortener.application.usecases.UseCase;
import com.daniel.shortener.core.entities.ShortUrl;
import com.daniel.shortener.core.exceptions.NotFoundException;
import com.daniel.shortener.core.repositories.AsyncCacheRepository;
import com.daniel.shortener.core.repositories.Repository;

public final class FindUrlUseCase implements UseCase<String, CompletableFuture<ShortUrl>> {

    private final AsyncCacheRepository<ShortUrl, String> cacheRepository;
    private final Repository<ShortUrl, String> repository;

    public FindUrlUseCase(AsyncCacheRepository<ShortUrl, String> cacheRepository, Repository<ShortUrl, String> repository) {
        this.cacheRepository = cacheRepository;
        this.repository = repository;
    }

    @Override
    public CompletableFuture<ShortUrl> execute(String slug) {
        //ShortUrl shortUrl = cacheRepository.getOrElse(slug, repository::find).orElseThrow(() -> new NotFoundException("Resource not found."));
        //ShortUrl shortUrl = repository.find(slug).orElseThrow(() -> new NotFoundException("Resource not found."));
        CompletableFuture<ShortUrl> future = cacheRepository.getOrElse(slug, s -> CompletableFuture.supplyAsync(() -> repository.find(s)))
            .thenApply(optional -> optional.orElseThrow(() -> new NotFoundException("Resource not found")));
        return future;
    }
    
}
