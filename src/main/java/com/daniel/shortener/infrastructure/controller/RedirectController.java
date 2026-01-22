package com.daniel.shortener.infrastructure.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daniel.shortener.application.usecases.FindUrlUseCase;
import com.daniel.shortener.core.entity.URL;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/r")
@RequiredArgsConstructor
public class RedirectController {
    
    private final FindUrlUseCase useCase;

    @GetMapping("/{slug}")
    public ResponseEntity<Object> redirect(@PathVariable String slug) {
        URL url = useCase.execute(slug);
        return ResponseEntity.status(HttpStatus.FOUND).header("Location", url.getOriginalUrl()).build();
    }

    //Without redirect for testing purposes
    @GetMapping("/test/{slug}") 
    public ResponseEntity<Object> redirectTest(@PathVariable String slug) {
        URL url = useCase.execute(slug);
        return ResponseEntity.status(HttpStatus.FOUND).build();
    }
 
}
