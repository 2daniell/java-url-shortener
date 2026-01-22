package com.daniel.shortener.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.daniel.shortener.application.usecases.CreateUrlUseCase;
import com.daniel.shortener.application.usecases.DeleteUrlUseCase;
import com.daniel.shortener.application.usecases.FindUrlUseCase;
import com.daniel.shortener.core.cache.CacheManager;
import com.daniel.shortener.core.gateway.UrlGateway;
import com.daniel.shortener.core.generator.SlugGenerator;

@Configuration
public class Configurations {

    @Bean
    public CreateUrlUseCase createUrlUseCase(SlugGenerator slugGenerator, UrlGateway urlGateway) {
        return new CreateUrlUseCase(slugGenerator, urlGateway);
    }

    @Bean
    public FindUrlUseCase findUrlUseCase(UrlGateway urlGateway, CacheManager cacheManager) {
        return new FindUrlUseCase(urlGateway, cacheManager);
    }

    @Bean
    public DeleteUrlUseCase deleteUrlUseCase(UrlGateway urlGateway, CacheManager cacheManager) {
        return new DeleteUrlUseCase(urlGateway, cacheManager);
    }
    
}
