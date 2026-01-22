package com.daniel.shortener.infrastructure.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daniel.shortener.application.usecases.CreateUrlUseCase;
import com.daniel.shortener.application.usecases.DeleteUrlUseCase;
import com.daniel.shortener.application.usecases.FindUrlUseCase;
import com.daniel.shortener.core.entity.URL;
import com.daniel.shortener.infrastructure.controller.response.CreateUrlResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UrlController {

    private final CreateUrlUseCase createUrlUseCase;
    private final DeleteUrlUseCase deleteUrlUseCase;
    private final FindUrlUseCase findUrlUseCase;

    @DeleteMapping("/{slug}")
    public ResponseEntity<Void> delete(@PathVariable String slug) {
        URL url = findUrlUseCase.execute(slug);
        boolean isDeleted = deleteUrlUseCase.execute(url);
        return ResponseEntity.status(isDeleted ? HttpStatus.OK : HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    public ResponseEntity<CreateUrlResponse> create(@RequestBody Map<String, String> request) {
        String destination = request.get("destination");
        URL url = createUrlUseCase.execute(destination);

        final String shortUrl = "http://localhost:8080/r/" + url.getSlug().getValue();
        
        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateUrlResponse(
            url.getId(),
            url.getOriginalUrl(),
            shortUrl,
            url.getCreatedAt()
        ));
    }

    
}
