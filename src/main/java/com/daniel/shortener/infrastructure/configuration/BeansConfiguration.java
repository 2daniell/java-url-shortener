package com.daniel.shortener.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.daniel.shortener.application.usecases.impl.CreateUrlUseCase;
import com.daniel.shortener.application.usecases.impl.DeleteUrlUseCase;
import com.daniel.shortener.application.usecases.impl.FindUrlUseCase;
import com.daniel.shortener.core.entities.ShortUrl;
import com.daniel.shortener.core.generators.SlugGenerator;
import com.daniel.shortener.core.repositories.Repository;
import com.daniel.shortener.infrastructure.generators.RandomSlugGenerator;

@Configuration
public class BeansConfiguration {

    @Bean
    public SlugGenerator slugGenerator() {
        return new RandomSlugGenerator();
    }

    @Bean
    public DeleteUrlUseCase deleteUrlUseCase(Repository<ShortUrl, String> repository) {
        return new DeleteUrlUseCase(repository);
    }

    @Bean
    public FindUrlUseCase findUrlUseCase(Repository<ShortUrl, String> repository) {
        return new FindUrlUseCase(repository);
    }

    @Bean CreateUrlUseCase createUrlUseCase(SlugGenerator generator, Repository<ShortUrl, String> repository) {
        return new CreateUrlUseCase(generator, repository);
    }
    
}
