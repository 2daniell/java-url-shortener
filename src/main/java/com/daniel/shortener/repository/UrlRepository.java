package com.daniel.shortener.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daniel.shortener.entity.ShortURL;
import com.daniel.shortener.model.Slug;

@Repository
public interface UrlRepository extends JpaRepository<ShortURL, Slug> {
    
}
