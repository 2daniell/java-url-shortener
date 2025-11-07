package com.daniel.shortener.infrastructure.repositories.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "short_urls")
@Entity
public class ShortUrlModel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String slug;
    private String destination;

    public ShortUrlModel() {}

    public ShortUrlModel(Long id, String slug, String destination) {
        this.id = id;
        this.slug = slug;
        this.destination = destination;
    }

    public ShortUrlModel(String slug, String destination) {
        this.slug = slug;
        this.destination = destination;
    }

    public String getDestination() {
        return destination;
    }

    public Long getId() {
        return id;
    }

    public String getSlug() {
        return slug;
    }
    
}
