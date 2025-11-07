package com.daniel.shortener.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daniel.shortener.entity.ShortUrl;
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
        
        Optional<ShortUrl> optional = service.findBySlug(slug);

        return optional.map((url) -> {
            
            String destination = url.getDestination();

            return ResponseEntity.status(HttpStatus.FOUND).header("Location", destination).build();

        }).orElse(
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        );
    }
}
