package com.daniel.shortener.application.usecases.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.daniel.shortener.core.entities.ShortUrl;
import com.daniel.shortener.core.entities.Slug;
import com.daniel.shortener.core.exceptions.NotFoundException;
import com.daniel.shortener.core.repositories.Repository;

public class DeleteUrlUseCaseTest {

    @Test
    void shouldDeleteWhenSlugExists() {
        Repository<ShortUrl, String> repository = mock(Repository.class);

        final String slugValue = "awr123";
        final String destination = "https://youtube.com";

        ShortUrl entity = new ShortUrl(Slug.of(slugValue), destination);
        when(repository.find(slugValue)).thenReturn(Optional.of(entity));

        DeleteUrlUseCase useCase = new DeleteUrlUseCase(repository);

        useCase.execute(slugValue);

        verify(repository, times(1)).find(slugValue);
        verify(repository, times(1)).delete(entity);

    }

    @Test
    void shouldThrowNotFoundExceptionWhenSlugDoesNotExists() {
        Repository<ShortUrl, String> repository = mock(Repository.class);

        final String slugValue = "not123";

        when(repository.find(slugValue)).thenReturn(Optional.empty());

        DeleteUrlUseCase useCase = new DeleteUrlUseCase(repository);

        assertThrows(NotFoundException.class, () -> useCase.execute(slugValue));

        verify(repository, times(1)).find(slugValue);
        verify(repository, never()).delete(any());


    }
}
