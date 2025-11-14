package com.daniel.shortener.application.usecases.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.daniel.shortener.core.entities.ShortUrl;
import com.daniel.shortener.core.entities.Slug;
import com.daniel.shortener.core.generators.SlugGenerator;
import com.daniel.shortener.core.repositories.Repository;

public class CreateUrlUseCaseTest {

    @Test
    void shouldCreateShortUrlWithUniqueSlug() {
        SlugGenerator slugGenerator = mock(SlugGenerator.class);
        Repository<ShortUrl, String> repository = mock(Repository.class);

        when(slugGenerator.generate())
            .thenReturn(Slug.of("abc123"))
            .thenReturn(Slug.of("xyz123"));

        when(repository.find("abc123")).thenReturn(Optional.of(new ShortUrl(Slug.of("abc123"), "ignored")));
        when(repository.find("xyz123")).thenReturn(Optional.empty());

        when(repository.save(Mockito.any())).thenAnswer(e -> e.getArgument(0));

        CreateUrlUseCase useCase = new CreateUrlUseCase(slugGenerator, repository);
        ShortUrl result = useCase.execute("https://google.com");

        assertEquals("xyz123", result.getSlug().getValue());
        assertEquals("https://google.com", result.getDestination());

        verify(slugGenerator, times(2)).generate();
        verify(repository, times(1)).find("abc123");
        verify(repository, times(1)).find("xyz123");
        verify(repository, times(1)).save(Mockito.any(ShortUrl.class));
    }
}
