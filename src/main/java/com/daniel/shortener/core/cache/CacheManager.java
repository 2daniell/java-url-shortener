package com.daniel.shortener.core.cache;

import java.util.Optional;
import java.util.function.Supplier;

import com.daniel.shortener.core.entity.URL;

public interface CacheManager {

    Optional<URL> get(String key);
    URL getOrLoad(String key, Supplier<Optional<URL>> loader);
    void evict(String key);
    
}
