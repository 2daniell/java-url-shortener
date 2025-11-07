package com.daniel.shortener.entity;

import com.daniel.shortener.model.Slug;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "short_urls")
@Entity 
public class ShortUrl {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String slug;
    private String destination;


    protected ShortUrl() {}

    public ShortUrl(Slug slug, String destination) {
        this.slug = slug.getSlug();
        this.destination = destination;
    }

    public String getSlug() {
        return slug;
    }

    public String getDestination() {
        return destination;
    }


}
