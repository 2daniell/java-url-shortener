package com.daniel.shortener.infrastructure.cache;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.springframework.stereotype.Component;

import com.daniel.shortener.core.cache.CacheManager;
import com.daniel.shortener.core.entity.URL;
import com.daniel.shortener.core.exceptions.NotFoundException;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

@Component
public class CaffeineCacheManager implements CacheManager {

    private final Cache<String, URL> cache;

    public CaffeineCacheManager() {
        this.cache = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.HOURS)
                .maximumSize(50_000)
                .initialCapacity(10_000)
                .build();
    }

    @Override
    public Optional<URL> get(String key) {
        return Optional.ofNullable(cache.getIfPresent(key));
    }

    @Override
    public URL getOrLoad(String key, Supplier<Optional<URL>> loader) {
        return cache.get(key, ignored -> loader.get().orElseThrow(() -> new NotFoundException("URL not found.")));
    }

    @Override
    public void evict(String key) {
        cache.invalidate(key);
    }
    
}
