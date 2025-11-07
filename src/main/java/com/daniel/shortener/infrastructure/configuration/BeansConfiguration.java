package com.daniel.shortener.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.daniel.shortener.application.usecases.impl.CreateUrlUseCase;
import com.daniel.shortener.application.usecases.impl.DeleteUrlUseCase;
import com.daniel.shortener.application.usecases.impl.FindUrlUseCase;
import com.daniel.shortener.core.entities.ShortUrl;
import com.daniel.shortener.core.generators.SlugGenerator;
import com.daniel.shortener.core.repositories.CacheRepository;
import com.daniel.shortener.core.repositories.Repository;
import com.daniel.shortener.infrastructure.generators.RandomSlugGenerator;
import com.daniel.shortener.infrastructure.repositories.RedisCacheRepository;
import com.daniel.shortener.infrastructure.repositories.serializer.EntitySerializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

@Configuration
public class BeansConfiguration {

    @Bean
    public SlugGenerator slugGenerator() {
        return new RandomSlugGenerator();
    }

    @Bean
    public EntitySerializer<ShortUrl> entitySerializer(ObjectMapper mapper) {
        return new EntitySerializer<ShortUrl>(mapper, ShortUrl.class);
    }

    @Bean(destroyMethod = "close")
    public RedisClient redisClient() {
        return RedisClient.create("redis://localhost:6379");
    }

    @Bean(destroyMethod = "close")
    public StatefulRedisConnection<String, String> connection(RedisClient redisClient) {
        return redisClient.connect();
    }

    @Bean
    public RedisCommands<String, String> redisCommands(StatefulRedisConnection<String, String> connection) {
        return connection.sync();
    }

    @Bean
    public CacheRepository<ShortUrl, String> cacheRepository(RedisCommands<String, String> redisCommands, EntitySerializer<ShortUrl> entitySerializer) {
        return new RedisCacheRepository(20, 3600, 300, redisCommands, entitySerializer);
    }

    @Bean
    public DeleteUrlUseCase deleteUrlUseCase(Repository<ShortUrl, String> repository) {
        return new DeleteUrlUseCase(repository);
    }

    @Bean
    public FindUrlUseCase findUrlUseCase(CacheRepository<ShortUrl, String> cacheRepository, Repository<ShortUrl, String> repository) {
        return new FindUrlUseCase(cacheRepository, repository);
    }

    @Bean CreateUrlUseCase createUrlUseCase(SlugGenerator generator, Repository<ShortUrl, String> repository) {
        return new CreateUrlUseCase(generator, repository);
    }
    
}
