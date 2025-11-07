package com.daniel.shortener.interfaces.rest.controllers;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daniel.shortener.application.usecases.impl.FindUrlUseCase;

@RestController
@RequestMapping("/r")
public class RedirectController {

    private final FindUrlUseCase findUrlUseCase;

    public RedirectController(FindUrlUseCase findUrlUseCase) {
        this.findUrlUseCase = findUrlUseCase;
    }
    
    @GetMapping("/{slug}")
    public CompletableFuture<ResponseEntity<Object>> redirect(@PathVariable String slug) {
        return findUrlUseCase.execute(slug).thenApply(url -> ResponseEntity.status(HttpStatus.FOUND).header("Location", url.getDestination()).build());
    }

    @GetMapping("/test/{slug}")
    public CompletableFuture<ResponseEntity<Object>> redirectTest(@PathVariable String slug) {
        return findUrlUseCase.execute(slug).thenApply(url -> ResponseEntity.status(HttpStatus.OK).build());
    }
}
