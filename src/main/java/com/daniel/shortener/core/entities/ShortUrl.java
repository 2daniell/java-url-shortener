package com.daniel.shortener.core.entities;

public class ShortUrl {

    private Long id;
    private Slug slug;
    private String destination;

    public ShortUrl() {}
    
    public ShortUrl(Slug slug, String destination) {
        this.slug = slug;
        this.destination = destination;
    }
    
    public ShortUrl(Long id, Slug slug, String destination) {
        this.id = id;
        this.slug = slug;
        this.destination = destination;
    }

    public Long getId() {
        return id;
    }
    
    public Slug getSlug() {
        return slug;
    }

    public String getDestination() {
        return destination;
    }    
    
}
