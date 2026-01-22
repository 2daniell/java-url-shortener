package com.daniel.shortener.infrastructure.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daniel.shortener.infrastructure.persistence.model.UrlModel;

@Repository
public interface UrlRepository extends JpaRepository<UrlModel, Long> {

    Optional<UrlModel> findBySlug(String slug);
    
    void deleteBySlug(String slug);

    boolean existsBySlug(String slug);
}
