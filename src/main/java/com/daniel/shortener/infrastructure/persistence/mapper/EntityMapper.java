package com.daniel.shortener.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import com.daniel.shortener.core.entities.ShortUrl;
import com.daniel.shortener.core.entities.Slug;
import com.daniel.shortener.infrastructure.persistence.model.ShortUrlModel;

@Component
public final class EntityMapper {

    public ShortUrlModel toModel(ShortUrl entity) {
        return new ShortUrlModel(entity.getId(), entity.getSlug().getValue(), entity.getDestination());
    }

    public ShortUrl toEntity(ShortUrlModel model) {
        return new ShortUrl(model.getId(), Slug.of(model.getSlug()), model.getDestination());
    }
    
}
