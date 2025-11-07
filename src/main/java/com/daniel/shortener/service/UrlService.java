package com.daniel.shortener.service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.daniel.shortener.entity.ShortUrl;
import com.daniel.shortener.exception.UrlNotFoundException;
import com.daniel.shortener.model.Slug;
import com.daniel.shortener.repository.UrlRepository;

@Service
public class UrlService {

    private final UrlRepository repository;
    
    public UrlService(UrlRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public String create(String destination) {
        
        Slug slug;

        do {
            slug = Slug.newRandomSlug();
        } while(findBySlug(slug.getSlug()).isPresent());

        ShortUrl shortUrl = new ShortUrl(slug, destination);

        repository.save(shortUrl);

        return shortUrl.getSlug();
    }

    @Transactional
    public void deleteBySlug(String slug) {
        Optional<ShortUrl> optional = findBySlug(slug);
        if (!optional.isPresent()) {
            throw new UrlNotFoundException(HttpStatus.NOT_FOUND, "Resource not found");
        }
        repository.delete(optional.get());
    }

    public Optional<ShortUrl> findBySlug(String slug) {
        return repository.findBySlug(slug);
    }
    
}
