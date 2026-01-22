package com.daniel.shortener.infrastructure.generator;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import com.daniel.shortener.core.entity.Slug;
import com.daniel.shortener.core.generator.SlugGenerator;

@Component
public class RandomSlugGenerator implements SlugGenerator {

    @Override
    public Slug generate() {
        String alphaNumeric = RandomStringUtils.insecure().nextAlphanumeric(Slug.LENGTH).toLowerCase();
        return Slug.of(alphaNumeric);
    }
}