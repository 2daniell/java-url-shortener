package com.daniel.shortener.infrastructure.repositories;

import java.lang.foreign.Linker.Option;
import java.util.Optional;
import java.util.function.Function;

import com.daniel.shortener.core.entities.ShortUrl;
import com.daniel.shortener.core.repositories.CacheRepository;
import com.daniel.shortener.infrastructure.repositories.serializer.EntitySerializer;
import io.lettuce.core.api.sync.RedisCommands;

public final class RedisCacheRepository implements CacheRepository<ShortUrl, String> {

    private final int frequencyThreshold;
    private final int ttlSeconds;
    private final int ttlCounterSeconds;

    private final RedisCommands<String, String> redis;
    private final EntitySerializer<ShortUrl> serializer;

    public RedisCacheRepository(int frequencyThreshold, int ttlSeconds, int ttlCounterSeconds,
            RedisCommands<String, String> redis, EntitySerializer<ShortUrl> serializer) {
        this.frequencyThreshold = frequencyThreshold;
        this.ttlSeconds = ttlSeconds;
        this.ttlCounterSeconds = ttlCounterSeconds;
        this.redis = redis;
        this.serializer = serializer;
    }

    @Override
    public Optional<ShortUrl> get(String key) {
        String cacheKey = cacheKey(key);
        String cachedValue = redis.get(cacheKey);
        return (cachedValue == null) ? Optional.empty() : Optional.of(serializer.deserialize(cachedValue));
    }

    @Override
    public Optional<ShortUrl> getOrElse(String key, Function<String, Optional<ShortUrl>> loader) {
        Optional<ShortUrl> redisResult = get(key);
        if (redisResult.isPresent()) {
            return redisResult;
        }

        Optional<ShortUrl> databaseResult = loader.apply(key);

        databaseResult.ifPresent((url) -> {
            String counterKey = counterKey(key);
            long count = redis.incr(counterKey);
            redis.expire(counterKey, ttlCounterSeconds);

            if (count >= frequencyThreshold) {
                System.out.println("\n\nREGISTRO CACHEADO\n\n");  
                put(key, url);
            } 
        });

        return databaseResult;
    }

    @Override
    public void put(String key, ShortUrl value) {
        String serialized = serializer.serialize(value);
        String cacheKey = cacheKey(key);
        redis.setex(cacheKey, ttlSeconds, serialized);
    }

    @Override
    public void evict(String key) {
        String counterKey = counterKey(key);
        String cacheKey = cacheKey(key);
        redis.del(cacheKey, counterKey);
    }

    private String counterKey(String key) {
        return "hits:" + key;
    }

    public String cacheKey(String key) {
        return "cache:" + key;
    }
    
}
