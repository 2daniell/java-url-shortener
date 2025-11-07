package com.daniel.shortener.interfaces.rest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daniel.shortener.application.usecases.impl.FindUrlUseCase;
import com.daniel.shortener.core.entities.ShortUrl;

@RestController
@RequestMapping("/r")
public class RedirectController {

    private final FindUrlUseCase findUrlUseCase;

    public RedirectController(FindUrlUseCase findUrlUseCase) {
        this.findUrlUseCase = findUrlUseCase;
    }
    
    @GetMapping("/{slug}")
    public ResponseEntity<Object> redirect(@PathVariable String slug) {
        ShortUrl shortUrl = findUrlUseCase.execute(slug);
        String destination = shortUrl.getDestination();
        return ResponseEntity.status(HttpStatus.FOUND).header("Location", destination).build();
    }

    @GetMapping("/test/{slug}")
    public ResponseEntity<Object> redirectTest(@PathVariable String slug) {
        ShortUrl shortUrl = findUrlUseCase.execute(slug);
        String destination = shortUrl.getDestination();
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
