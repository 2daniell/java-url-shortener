package com.daniel.shortener.application.usecases.impl;

import com.daniel.shortener.application.usecases.ActionUseCase;
import com.daniel.shortener.core.entities.ShortUrl;
import com.daniel.shortener.core.exceptions.NotFoundException;
import com.daniel.shortener.core.repositories.Repository;

public final class DeleteUrlUseCase implements ActionUseCase<String> {

    private final Repository<ShortUrl, String> repository;

    public DeleteUrlUseCase(Repository<ShortUrl, String> repository) {
        this.repository = repository;
    }

    @Override
    public void execute(String slug) {
        ShortUrl shortUrl = repository.find(slug).orElseThrow(() -> new NotFoundException("Resource not found."));
        repository.delete(shortUrl);
    }

    
}
