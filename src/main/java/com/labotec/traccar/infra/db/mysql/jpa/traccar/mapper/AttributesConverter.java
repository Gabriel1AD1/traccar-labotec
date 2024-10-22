package com.labotec.traccar.infra.db.mysql.jpa.traccar.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.labotec.traccar.infra.db.mysql.jpa.traccar.entity.Attributes;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;

@Converter(autoApply = true)
public class AttributesConverter implements AttributeConverter<Attributes, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Attributes attributes) {
        try {
            return objectMapper.writeValueAsString(attributes);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting Attributes to JSON", e);
        }
    }

    @Override
    public Attributes convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, Attributes.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to Attributes", e);
        }
    }
}
