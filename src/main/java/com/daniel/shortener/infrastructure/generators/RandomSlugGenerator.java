package com.daniel.shortener.infrastructure.generators;

import org.apache.commons.lang3.RandomStringUtils;

import com.daniel.shortener.core.entities.Slug;
import com.daniel.shortener.core.generators.SlugGenerator;

public class RandomSlugGenerator implements SlugGenerator {

    @Override
    public Slug generate() {
        String alphaNumeric = RandomStringUtils.insecure().nextAlphanumeric(6).toLowerCase();
        return Slug.of(alphaNumeric);
    }
    
}
