package com.daniel.shortener.model;

import java.io.Serializable;
import java.util.Objects;

import org.apache.commons.lang3.RandomStringUtils;

import jakarta.persistence.Embeddable;

@Embeddable
public class Slug implements Serializable {
    
    private String slug;

    protected Slug() {}

    public Slug(String slug) {
        if (!slug.matches("^[A-Za-z0-9]+$")) {
            throw new IllegalArgumentException("Invalid slug");
        }
        this.slug = slug;
    }

    public String getSlug() {
        return this.slug;
    }

    public static Slug newRandomSlug() {
        return new Slug(RandomStringUtils.secure().nextAlphanumeric(6));
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Slug slug = (Slug) o;
        return slug.getSlug().equals(this.slug);
    }

    @Override
    public int hashCode() {
        return Objects.hash(slug);
    }

    @Override
    public String toString() {
        return slug;
    }



}
