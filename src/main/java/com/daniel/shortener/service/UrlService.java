package com.daniel.shortener.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.daniel.shortener.entity.ShortURL;
import com.daniel.shortener.model.Slug;
import com.daniel.shortener.repository.UrlRepository;

@Service
public class UrlService {

    private final UrlRepository repository;
    
    public UrlService(UrlRepository repository) {
        this.repository = repository;
    }

    String temp = "";

    @Transactional
    public String create(String destination) {

        Slug slug = Slug.newRandomSlug();
        
        ShortURL url = new ShortURL(slug, destination);
        repository.save(url);

        return slug.getSlug();
    }

    @Transactional
    public void deleteBySlug(String slug) {
        repository.deleteById(new Slug(slug));
    }
    
    public Optional<ShortURL> getDestination(String slug) {
        return repository.findById(new Slug(slug));
    }
    
}
