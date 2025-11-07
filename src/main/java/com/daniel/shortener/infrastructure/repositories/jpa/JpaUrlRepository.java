package com.daniel.shortener.infrastructure.repositories.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daniel.shortener.infrastructure.repositories.model.ShortUrlModel;

@Repository
public interface JpaUrlRepository extends JpaRepository<ShortUrlModel, Long> {

    Optional<ShortUrlModel> findBySlug(String slug);
    
}
