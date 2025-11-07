package com.daniel.shortener.infrastructure.repositories;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import com.daniel.shortener.core.entities.ShortUrl;
import com.daniel.shortener.core.repositories.AsyncCacheRepository;
import com.daniel.shortener.infrastructure.repositories.serializer.EntitySerializer;

import io.lettuce.core.api.async.RedisAsyncCommands;

public final class AsyncRedisCacheRepository implements AsyncCacheRepository<ShortUrl, String> {

    private final int frequencyThreshold;
    private final int ttlSeconds;
    private final int ttlCounterSeconds;

    private final RedisAsyncCommands<String, String> redis;
    private final EntitySerializer<ShortUrl> serializer;

    private final ConcurrentHashMap<String, CompletableFuture<Optional<ShortUrl>>> pendingCacheRequest = new ConcurrentHashMap<>();

    public AsyncRedisCacheRepository(int frequencyThreshold, int ttlSeconds, int ttlCounterSeconds,
            RedisAsyncCommands<String, String> redis, EntitySerializer<ShortUrl> serializer) {
        this.frequencyThreshold = frequencyThreshold;
        this.ttlSeconds = ttlSeconds;
        this.ttlCounterSeconds = ttlCounterSeconds;
        this.redis = redis;
        this.serializer = serializer;
    }

    @Override
    public CompletableFuture<Optional<ShortUrl>> get(String key) {
        CompletableFuture<Optional<ShortUrl>> future = pendingCacheRequest.get(key);
        if (future != null) {
            return future;
        }

        CompletableFuture<Optional<ShortUrl>> newFuture = redis.get(cacheKey(key))
            .thenApply(value -> value == null ? Optional.<ShortUrl>empty() : Optional.of(serializer.deserialize(value)))
            .toCompletableFuture();
        
        CompletableFuture<Optional<ShortUrl>> existing = pendingCacheRequest.putIfAbsent(key, newFuture);
        if (existing != null) {
            return existing;
        }

        newFuture.whenComplete((res, ex) -> pendingCacheRequest.remove(key));

        return newFuture;
    }

    @Override
    public CompletableFuture<Optional<ShortUrl>> getOrElse(String key, Function<String, CompletableFuture<Optional<ShortUrl>>> loader) {
        return get(key).thenCompose(cached -> {
            if (cached.isPresent()) {
                return CompletableFuture.completedFuture(cached);
            }

            return loader.apply(key).thenCompose(databaseResult -> {
                if (databaseResult.isPresent()) {

                    String counterKey = counterKey(key);
                    return redis.incr(counterKey).thenCompose(count -> redis.expire(counterKey, ttlCounterSeconds).thenCompose(exp -> {
                        if (count >= frequencyThreshold) {
                            return put(key, databaseResult.get()).thenApply(value -> databaseResult);
                        }
                        return CompletableFuture.completedFuture(databaseResult);
                    }));
                } else {
                    return CompletableFuture.completedFuture(databaseResult);
                }
            });
        });
    }

    @Override
    public CompletableFuture<String> put(String key, ShortUrl value) {
        String serialized = serializer.serialize(value);
        String cacheKey = cacheKey(key);
        return redis.setex(cacheKey, ttlSeconds, serialized).toCompletableFuture();
    }

    @Override
    public CompletableFuture<Void> evict(String key) {
        String counterKey = counterKey(key);
        String cacheKey = cacheKey(key);
        return redis.del(counterKey, cacheKey).thenApply(r -> (Void)null).toCompletableFuture();
    }
    
    private String counterKey(String key) {
        return "hits:" + key;
    }

    public String cacheKey(String key) {
        return "cache:" + key;
    }
    
}
