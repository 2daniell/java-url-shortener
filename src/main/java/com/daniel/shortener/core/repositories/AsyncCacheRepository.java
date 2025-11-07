package com.daniel.shortener.core.repositories;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public interface AsyncCacheRepository<T, ID> {

    CompletableFuture<Optional<T>> get(ID key);
    CompletableFuture<Optional<T>> getOrElse(ID key, Function<ID, CompletableFuture<Optional<T>>> loader);
    CompletableFuture<ID> put(ID key, T value);
    CompletableFuture<Void> evict(ID key);
}
