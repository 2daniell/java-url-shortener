package com.daniel.shortener.infrastructure.gateway;

import org.springframework.stereotype.Component;

import com.daniel.shortener.core.entity.Slug;
import com.daniel.shortener.core.entity.URL;
import com.daniel.shortener.infrastructure.persistence.model.UrlModel;

@Component
public final class UrlMapper {

    public URL toEntity(UrlModel model) {
        return new URL(model.getId(), Slug.of(model.getSlug()), model.getOriginalUrl(), model.getUpdatedAt(), model.getCreatedAt());
    }

    public UrlModel toModel(URL entity) {
        return new UrlModel(entity.getId(), entity.getSlug().getValue(), entity.getOriginalUrl(), entity.getUpdatedAt(), entity.getCreatedAt());
    }    
}
