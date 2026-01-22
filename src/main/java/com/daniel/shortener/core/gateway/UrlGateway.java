package com.daniel.shortener.core.gateway;

import java.util.Optional;

import com.daniel.shortener.core.entity.URL;

public interface UrlGateway {
    
    URL save(URL url);
    Optional<URL> findBySlug(String slug);
    void delete(URL url);
    boolean existsById(Long id);
    boolean existsBySlug(String slug);

}
