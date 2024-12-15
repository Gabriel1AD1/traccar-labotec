package com.labotec.traccar.app.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

    // Configuración avanzada del ObjectMapper
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false) // Ignorar propiedades desconocidas
            .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);  // Insensibilidad a mayúsculas/minúsculas

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

    /**
     * Convierte una cadena JSON en un objeto de tipo T.
     *
     * @param json La cadena JSON.
     * @param clazz El tipo de objeto al que se desea convertir.
     * @param <T> El tipo de objeto.
     * @return El objeto convertido.
     * @throws RuntimeException Si ocurre un error durante la conversión.
     */
    public static <T> T toObject(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Error al convertir el JSON a objeto: " + e.getMessage() + "\nJSON: " + json, e);
        }
    }

    /**
     * Convierte un objeto de tipo T en un objeto de tipo R.
     *
     * @param object El objeto de entrada.
     * @param targetClass La clase del tipo objetivo.
     * @param <T> El tipo del objeto de entrada.
     * @param <R> El tipo del objeto de salida.
     * @return El objeto convertido.
     * @throws RuntimeException Si ocurre un error durante la conversión.
     */
    public static <T, R> R convertObject(T object, Class<R> targetClass) {
        try {
            String json = toJson(object); // Convertir a JSON intermedio
            return toObject(json, targetClass); // Convertir al tipo objetivo
        } catch (Exception e) {
            throw new RuntimeException("Error al convertir el objeto entre tipos", e);
        }
    }
}
