package com.daniel.shortener.entity;

import com.daniel.shortener.model.Slug;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class ShortURL {
    
    @EmbeddedId
    private Slug slug;

    private String destination;


    protected ShortURL() {}

    public ShortURL(Slug slug, String destination) {
        this.slug = slug;
        this.destination = destination;
    }

    public ShortURL(String slug, String destination) {
        this(new Slug(slug), destination);
    }

    public Slug getSlug() {
        return slug;
    }

    public String getDestination() {
        return destination;
    }


}
