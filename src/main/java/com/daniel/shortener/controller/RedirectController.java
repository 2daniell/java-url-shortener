package com.daniel.shortener.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daniel.shortener.entity.ShortUrl;
import com.daniel.shortener.exception.UrlNotFoundException;
import com.daniel.shortener.service.UrlService;

@RestController
@RequestMapping("/r")
public class RedirectController {
    
    private final UrlService service;

    public RedirectController(UrlService service) {
        this.service = service;
    }

    @GetMapping("/{slug}")
    public ResponseEntity<Object> redirect(@PathVariable String slug) {
        
        ShortUrl shortUrl = service.findBySlug(slug).orElseThrow(() -> new UrlNotFoundException(HttpStatus.NOT_FOUND, "Resource not found"));
        String destination = shortUrl.getDestination();

        return ResponseEntity.status(HttpStatus.FOUND).header("Location", destination).build();
    }
}
