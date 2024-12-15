package com.labotec.traccar.app.utils;

import com.labotec.traccar.app.enums.RouteType;
import com.labotec.traccar.domain.web.labotec.request.create.RouteSegmentCreateDTO;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class RouteUtils {

    // Umbrales para determinar si la ruta es corta o larga (en kilómetros)
    private static final long SHORT_ROUTE_THRESHOLD_KM = 50;  // 50 km (distancia mínima para una ruta corta)
    private static final long LONG_ROUTE_THRESHOLD_KM = 70;  // 70 km (distancia mínima para una ruta larga)

    /**
     * Verifica si una ruta es corta, larga o normal basado en su distancia promedio.
     *
     * @param distanceMaxInKM Distancia máxima de la ruta en kilómetros.
     * @param distanceMinInKM Distancia mínima de la ruta en kilómetros.
     * @return El tipo de ruta: SHORT, NORMAL, LONG.
     */
    public static RouteType checkRouteLength(@NotNull Long distanceMaxInKM, @NotNull Long distanceMinInKM) {
        // Calcular el promedio de la distancia
        long routeDistanceKm = (distanceMaxInKM + distanceMinInKM) / 2;

        // Comparar con los umbrales predefinidos
        if (routeDistanceKm <= SHORT_ROUTE_THRESHOLD_KM) {
            return RouteType.SHORT;  // Ruta corta
        } else if (routeDistanceKm >= LONG_ROUTE_THRESHOLD_KM) {
            return RouteType.LONG;   // Ruta larga
        } else {
            return RouteType.NORMAL; // Ruta de distancia normal
        }
    }
    /**
     * Suma los tiempos mínimos de espera en la lista de segmentos.
     *
     * @param segments Lista de segmentos de ruta (RouteSegmentCreateDTO).
     * @return La suma total de los tiempos mínimos de espera.
     */
    public static int sumMinWaitTime(List<RouteSegmentCreateDTO> segments) {
        if (segments == null || segments.isEmpty()) {
            return 0; // Devuelve 0 si la lista está vacía o es nula.
        }

        return segments.stream()
                .filter(segment -> segment.getMinWaitTime() != null) // Ignora los nulos.
                .mapToInt(RouteSegmentCreateDTO::getMinWaitTime) // Obtiene el valor.
                .sum(); // Suma todos los valores.
    }

    /**
     * Suma los tiempos máximos de espera en la lista de segmentos.
     *
     * @param segments Lista de segmentos de ruta (RouteSegmentCreateDTO).
     * @return La suma total de los tiempos máximos de espera.
     */
    public static int sumMaxWaitTime(List<RouteSegmentCreateDTO> segments) {
        if (segments == null || segments.isEmpty()) {
            return 0; // Devuelve 0 si la lista está vacía o es nula.
        }

        return segments.stream()
                .filter(segment -> segment.getMaxWaitTime() != null) // Ignora los nulos.
                .mapToInt(RouteSegmentCreateDTO::getMaxWaitTime) // Obtiene el valor.
                .sum(); // Suma todos los valores.
    }
    public static int sumEstimatedNextTravelTime(List<RouteSegmentCreateDTO> segments) {
        if (segments == null || segments.isEmpty()) {
            return 0; // Devuelve 0 si la lista está vacía o es nula.
        }

        return segments.stream()
                .filter(segment -> segment.getEstimatedNextTravelTime() != null) // Ignora los nulos.
                .mapToInt(RouteSegmentCreateDTO::getEstimatedNextTravelTime) // Obtiene el valor.
                .sum(); // Suma todos los valores.
    }
}
