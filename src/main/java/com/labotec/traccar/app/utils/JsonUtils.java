package com.labotec.traccar.app.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Convierte cualquier objeto en una cadena JSON.
     *
     * @param object El objeto a convertir.
     * @return Una cadena JSON que representa el objeto.
     * @throws RuntimeException Si ocurre un error durante la conversión.
     */
    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException("Error al convertir el objeto a JSON", e);
        }
    }
}
