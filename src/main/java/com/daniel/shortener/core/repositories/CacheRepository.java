package com.daniel.shortener.core.repositories;

import java.util.Optional;
import java.util.function.Function;

public interface CacheRepository<T, ID> {

    Optional<T> get(ID key);
    Optional<T> getOrElse(ID key, Function<ID, Optional<T>> loader);
    void put(ID key, T value);
    void evict(ID key);
    
}
