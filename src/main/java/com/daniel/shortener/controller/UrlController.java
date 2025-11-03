package com.daniel.shortener.controller;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daniel.shortener.dto.ShortUrlCreateRequest;
import com.daniel.shortener.dto.ShortUrlCreateResponse;
import com.daniel.shortener.service.UrlService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/url")
public class UrlController {

    private final UrlService service;

    public UrlController(UrlService service) {
        this.service = service;
    }

    @DeleteMapping("/{slug}")
    public ResponseEntity<Void> delete(@PathVariable String slug) {
        service.deleteBySlug(slug);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping
    public ResponseEntity<ShortUrlCreateResponse> create(@RequestBody @Valid ShortUrlCreateRequest request) {
        
        String slug = service.create(request.destination());
        ShortUrlCreateResponse response = new ShortUrlCreateResponse(slug);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
}
