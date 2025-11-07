package com.daniel.shortener.infrastructure.repositories.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class EntitySerializer<T> {

    private final ObjectMapper mapper;
    private final Class<T> type;

    public EntitySerializer(ObjectMapper mapper, Class<T> type) {
        this.mapper = mapper;
        this.type = type;
    }

    public String serialize(T entity) {
        try {
            return mapper.writeValueAsString(entity);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao serializar a entidade", e);
        }
    }

    public T deserialize(String serialized) {
        try {
            return mapper.readValue(serialized, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao desserializar a entidade", e);
        }
    }
}
