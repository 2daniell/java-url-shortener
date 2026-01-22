package com.daniel.shortener.application.usecases;

import com.daniel.shortener.core.cache.CacheManager;
import com.daniel.shortener.core.entity.URL;
import com.daniel.shortener.core.gateway.UrlGateway;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteUrlUseCase implements UseCase<URL, Boolean> {
    
    private final UrlGateway gateway;
    private final CacheManager cacheManager;
    
    @Override
    public Boolean execute(URL url) {
        gateway.delete(url);
        cacheManager.evict(url.getSlug().getValue());
        return true;
    }
    
}
