package com.daniel.shortener.core.entity;

import com.daniel.shortener.core.exceptions.InvalidResourceException;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = "value")
public final class Slug {

    public static final int LENGTH = 8;
    
    private final String value;

    private Slug(String value) {
        if (!validate(value)) {
            throw new InvalidResourceException("Invalid Slug!");
        }
        this.value = value;
    }

    private boolean validate(String value) {
        return value != null && value.matches("^[a-zA-Z0-9]{" + LENGTH + "}$");
    }

    public static Slug of(String value) {
        return new Slug(value);
    }

}
