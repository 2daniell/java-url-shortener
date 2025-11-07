package com.daniel.shortener.infrastructure.persistence.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daniel.shortener.infrastructure.persistence.model.ShortUrlModel;

@Repository
public interface JpaUrlRepository extends JpaRepository<ShortUrlModel, Long> {

    Optional<ShortUrlModel> findBySlug(String slug);
    
}
