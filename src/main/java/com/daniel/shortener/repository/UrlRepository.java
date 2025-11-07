package com.daniel.shortener.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daniel.shortener.entity.ShortUrl;

@Repository
public interface UrlRepository extends JpaRepository<ShortUrl, Long> {
 
    Optional<ShortUrl> findBySlug(String slug);
}
