package com.daniel.shortener.infrastructure.gateway;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.daniel.shortener.core.entity.URL;
import com.daniel.shortener.core.gateway.UrlGateway;
import com.daniel.shortener.infrastructure.persistence.repository.UrlRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public final class UrlRepositoryGateway implements UrlGateway{

    private final UrlRepository repository;
    private final UrlMapper mapper;

    @Override
    public URL save(URL url) {
        return mapper.toEntity(repository.save(mapper.toModel(url)));
    }

    @Override
    public Optional<URL> findBySlug(String slug) {
        return repository.findBySlug(slug).map(mapper::toEntity);
    }

    @Override
    public void delete(URL url) {
        repository.delete(mapper.toModel(url));
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public boolean existsBySlug(String slug) {
        return repository.existsBySlug(slug);
    }
    
}
