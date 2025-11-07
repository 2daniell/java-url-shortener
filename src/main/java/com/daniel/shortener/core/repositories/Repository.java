package com.daniel.shortener.core.repositories;

import java.util.Optional;

public interface Repository<T, ID> {

    Optional<T> find(ID key);
    T save(T entity);
    void delete(T entity);
    
}
