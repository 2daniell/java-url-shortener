package com.daniel.shortener.interfaces.rest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daniel.shortener.application.usecases.impl.CreateUrlUseCase;
import com.daniel.shortener.application.usecases.impl.DeleteUrlUseCase;
import com.daniel.shortener.core.entities.ShortUrl;
import com.daniel.shortener.interfaces.rest.request.CreateShortUrlRequest;
import com.daniel.shortener.interfaces.rest.response.CreateShortUrlResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class UrlController {

    private final DeleteUrlUseCase deleteUrlUseCase;
    private final CreateUrlUseCase createUrlUseCase;

    public UrlController(DeleteUrlUseCase deleteUrlUseCase, CreateUrlUseCase createUrlUseCase) {
        this.deleteUrlUseCase = deleteUrlUseCase;
        this.createUrlUseCase = createUrlUseCase;
    }

    @DeleteMapping("/{slug}")
    public ResponseEntity<Void> delete(@PathVariable String slug) {
        deleteUrlUseCase.execute(slug);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping
    public ResponseEntity<CreateShortUrlResponse> create(@RequestBody @Valid CreateShortUrlRequest request) {        
        ShortUrl shortUrl = createUrlUseCase.execute(request.destination());
        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateShortUrlResponse(shortUrl.getSlug().getValue()));
    }
    
}
