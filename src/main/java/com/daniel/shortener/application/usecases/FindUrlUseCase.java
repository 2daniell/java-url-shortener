package com.daniel.shortener.application.usecases;

import com.daniel.shortener.core.cache.CacheManager;
import com.daniel.shortener.core.entity.URL;
import com.daniel.shortener.core.gateway.UrlGateway;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FindUrlUseCase implements UseCase<String, URL> {

    private final UrlGateway gateway;
    private final CacheManager cacheManager;

    @Override
    public URL execute(String slug) {
        return cacheManager.getOrLoad(slug, () -> gateway.findBySlug(slug));
    }
    
}
