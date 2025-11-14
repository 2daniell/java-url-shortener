package com.daniel.shortener.application.usecases.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

import com.daniel.shortener.core.entities.ShortUrl;
import com.daniel.shortener.core.entities.Slug;
import com.daniel.shortener.core.exceptions.NotFoundException;
import com.daniel.shortener.core.repositories.AsyncCacheRepository;
import com.daniel.shortener.core.repositories.Repository;

public class FindUrlUseCaseTest {

    @Test
    void shouldReturnFromCache() {
        AsyncCacheRepository<ShortUrl, String> asyncCacheRepository = mock(AsyncCacheRepository.class);
        Repository<ShortUrl, String> repository = mock(Repository.class);

        final String slugValue = "abc123";
        final String destination = "https://ignored.com";

        ShortUrl cached = new ShortUrl(Slug.of(slugValue), destination);

        when(asyncCacheRepository.getOrElse(eq(slugValue), any())).thenReturn(CompletableFuture.completedFuture(Optional.of(cached)));

        FindUrlUseCase useCase = new FindUrlUseCase(asyncCacheRepository, repository);

        ShortUrl result = useCase.execute(slugValue).join();

        assertEquals(slugValue, result.getSlug().getValue());
        assertEquals(destination, result.getDestination());

        verify(asyncCacheRepository, times(1)).getOrElse(eq(slugValue), any());
        verify(repository, never()).find(any());
    }

    @Test
    void shouldReturnFromRepositoryWhenCacheMiss() {
        AsyncCacheRepository<ShortUrl, String> asyncCacheRepository = mock(AsyncCacheRepository.class);
        Repository<ShortUrl, String> repository = mock(Repository.class);

        final String slugValue = "xyz123";
        final String destination = "https://google.com";

        ShortUrl stored = new ShortUrl(Slug.of(slugValue), destination);

        when(asyncCacheRepository.getOrElse(eq(slugValue), any())).thenAnswer(invocation -> {
            Function<String, CompletableFuture<Optional<ShortUrl>>> loader = invocation.getArgument(1);
            return loader.apply(slugValue);
        });

        when(repository.find(slugValue)).thenReturn(Optional.of(stored));

        FindUrlUseCase useCase = new FindUrlUseCase(asyncCacheRepository, repository);
        ShortUrl result = useCase.execute(slugValue).join();

        assertEquals(slugValue, result.getSlug().getValue());
        assertEquals(destination, result.getDestination());

        verify(asyncCacheRepository, times(1)).getOrElse(eq(slugValue), any());
        verify(repository, times(1)).find(slugValue);

    }

    @Test
    void shouldThrowNotFoundExceptionWhenNotInCacheNorRepository() {
        AsyncCacheRepository<ShortUrl, String> asyncCacheRepository = mock(AsyncCacheRepository.class);
        Repository<ShortUrl, String> repository = mock(Repository.class);

        final String slugValue = "kqj123";

        when(asyncCacheRepository.getOrElse(eq(slugValue), any())).thenAnswer(invocation -> {
            Function<String, CompletableFuture<Optional<ShortUrl>>> loader = invocation.getArgument(1);
            return loader.apply(slugValue);
        });

        when(repository.find(slugValue)).thenReturn(Optional.empty());

        FindUrlUseCase useCase = new FindUrlUseCase(asyncCacheRepository, repository);

        CompletionException ex = assertThrows(CompletionException.class, () -> useCase.execute(slugValue).join());

        assertTrue(ex.getCause() instanceof NotFoundException);

        verify(repository, times(1)).find(slugValue);
        verify(asyncCacheRepository, times(1)).getOrElse(eq(slugValue), any());
    }

}
