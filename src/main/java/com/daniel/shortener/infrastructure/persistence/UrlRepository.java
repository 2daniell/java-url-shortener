package com.daniel.shortener.infrastructure.persistence;

import java.util.Optional;

import com.daniel.shortener.core.entities.ShortUrl;
import com.daniel.shortener.core.repositories.Repository;
import com.daniel.shortener.infrastructure.persistence.jpa.JpaUrlRepository;
import com.daniel.shortener.infrastructure.persistence.mapper.EntityMapper;
import com.daniel.shortener.infrastructure.persistence.model.ShortUrlModel;

@org.springframework.stereotype.Repository
public class UrlRepository implements Repository<ShortUrl, String> {

    private final JpaUrlRepository repository; 
    private final EntityMapper mapper;

    public UrlRepository(JpaUrlRepository repository, EntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<ShortUrl> find(String key) {
        return repository.findBySlug(key).map(mapper::toEntity);
    }

    @Override
    public ShortUrl save(ShortUrl entity) {
        ShortUrlModel model = mapper.toModel(entity);
        return mapper.toEntity(repository.save(model));
    }

    @Override
    public void delete(ShortUrl entity) {
        repository.delete(mapper.toModel(entity));
    }
    
}
