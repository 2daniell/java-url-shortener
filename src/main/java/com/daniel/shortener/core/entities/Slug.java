package com.daniel.shortener.core.entities;

public final class Slug {
    
    private final String value;

    private Slug(String value) {
        if (!value.matches("^[A-Za-z0-9]+$")) {
            throw new IllegalArgumentException("Invalid slug");
        }
        this.value = value;
    }

    public static Slug of(String value) {
        return new Slug(value);
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Slug slug = (Slug) o;
        return slug.getValue().equals(this.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }

}
