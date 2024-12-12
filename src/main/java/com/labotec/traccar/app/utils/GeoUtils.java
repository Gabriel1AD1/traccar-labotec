package com.labotec.traccar.app.utils;

import com.labotec.traccar.app.enums.RouteType;
import com.labotec.traccar.domain.web.dto.labotec.response.BusStopResponse;

import java.util.List;

public class GeoUtils {

    private static final double EARTH_RADIUS = 6371000; // Radio de la Tierra en metros
    private static final double SHORT_ROUTE_THRESHOLD = 50000; // 50 km (en metros)

    // Método para determinar si un punto está dentro de la geocerca
    public static boolean isWithinGeofence(double lat1, double lon1, double latV, double lonV, double radius) {
        double deltaLat = Math.toRadians(latV - lat1);
        double deltaLon = Math.toRadians(lonV - lon1);

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(latV))
                * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS * c; // La distancia ya está en metros

        return distance <= radius; // Compara la distancia en metros con el radio
    }

    /**
     * Calcula la distancia entre dos coordenadas utilizando la fórmula de Haversine.
     * Este es el cálculo estándar, adecuado para rutas largas.
     *
     * @param lat1 Latitud del primer punto.
     * @param lon1 Longitud del primer punto.
     * @param lat2 Latitud del segundo punto.
     * @param lon2 Longitud del segundo punto.
     * @return La distancia en metros entre los dos puntos.
     */
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final double EARTH_RADIUS_METERS = 6371000; // Radio de la Tierra en metros

        // Convertir grados a radianes
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        // Calcular las diferencias entre las coordenadas
        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;

        // Aplicar la fórmula de Haversine
        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
                + Math.cos(lat1Rad) * Math.cos(lat2Rad)
                * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Calcular la distancia y devolverla en metros
        return EARTH_RADIUS_METERS * c; // Devuelve la distancia en metros
    }

    /**
     * Calcula la distancia entre dos coordenadas utilizando la fórmula de Vincenty.
     * Este cálculo es más preciso para distancias más cortas y medias.
     *
     * @param lat1 Latitud del primer punto.
     * @param lon1 Longitud del primer punto.
     * @param lat2 Latitud del segundo punto.
     * @param lon2 Longitud del segundo punto.
     * @return La distancia en metros entre los dos puntos.
     */
    public static double calculateDistanceVincenty(double lat1, double lon1, double lat2, double lon2) {
        final double a = 6378137.0; // Radio de la Tierra en metros
        final double f = 1 / 298.257223563; // Esfericidad
        final double b = (1 - f) * a; // Semi-eje menor

        double phi1 = Math.toRadians(lat1);
        double phi2 = Math.toRadians(lat2);
        double lambda1 = Math.toRadians(lon1);
        double lambda2 = Math.toRadians(lon2);

        double U1 = Math.atan((1 - f) * Math.tan(phi1));
        double U2 = Math.atan((1 - f) * Math.tan(phi2));
        double L = lambda2 - lambda1;

        double sinU1 = Math.sin(U1);
        double cosU1 = Math.cos(U1);
        double sinU2 = Math.sin(U2);
        double cosU2 = Math.cos(U2);

        double sinSigma, cosSigma, sigma, sinAlpha, cos2Alpha, cos2SigmaM, C;

        double lambda = L;
        double lambdaP;
        int iterLimit = 100;

        do {
            double sinLambda = Math.sin(lambda);
            double cosLambda = Math.cos(lambda);
            sinSigma = Math.sqrt(Math.pow(cosU2 * sinLambda, 2) +
                    Math.pow(cosU1 * sinU2 - sinU1 * cosU2 * cosLambda, 2));
            cosSigma = sinU1 * sinU2 + cosU1 * cosU2 * cosLambda;
            sigma = Math.atan2(sinSigma, cosSigma);

            sinAlpha = cosU1 * cosU2 * sinLambda / sinSigma;
            cos2Alpha = 1 - sinAlpha * sinAlpha;
            cos2SigmaM = cosSigma - 2 * sinU1 * sinU2 / cos2Alpha;

            C = f / 16 * cos2Alpha * (4 + f * (4 - 3 * cos2Alpha));
            lambdaP = lambda;
            lambda = L + (1 - C) * f * sinAlpha *
                    (sigma + C * sinSigma * (cos2SigmaM + C * cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM)));

        } while (Math.abs(lambda - lambdaP) > 1e-12 && --iterLimit > 0);

        double u2 = cos2Alpha * (a * a - b * b) / (b * b);
        double A = 1 + u2 / 16384 * (4096 + u2 * (-768 + u2 * (320 - 175 * u2)));
        double B = u2 / 1024 * (256 + u2 * (-128 + u2 * (74 - 47 * u2)));
        double deltaSigma = B * sinSigma * (cos2SigmaM + B / 4 * (cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM) -
                B / 6 * cos2SigmaM * (-3 + 4 * sinSigma * sinSigma) * (-3 + 4 * cos2SigmaM * cos2SigmaM)));

        return b * A * (sigma - deltaSigma); // Devuelve la distancia en metros
    }

    /**
     * Determina en qué paradero está el vehículo, basándose en el tipo de ruta (corta o larga).
     * Utiliza el algoritmo adecuado (Haversine o Vincenty) para el cálculo de la distancia.
     *
     * @param vehicleLat Latitud del vehículo.
     * @param vehicleLon Longitud del vehículo.
     * @param busStops Lista de paraderos.
     * @param proximityRadiusM Radio de proximidad en metros.
     * @param routeType Tipo de la ruta (Corta o Larga).
     * @return El paradero más cercano dentro del radio de proximidad.
     */
    public static BusStopResponse getBusStopResponseIdIfWithinProximity(double vehicleLat, double vehicleLon,
                                                                        List<BusStopResponse> busStops, double proximityRadiusM,
                                                                        RouteType routeType) {
        for (BusStopResponse busStop : busStops) {
            double busStopLat = busStop.getLatitude();
            double busStopLon = busStop.getLongitude();

            double distance;

            // Si la ruta es corta, usar Vincenty para mayor precisión
            if (routeType == RouteType.SHORT) {
                distance = calculateDistanceVincenty(vehicleLat, vehicleLon, busStopLat, busStopLon);
            } else if(routeType == RouteType.LONG){ // Si es una ruta larga, usar Haversine
                distance = calculateDistance(vehicleLat, vehicleLon, busStopLat, busStopLon);
            }else {
                distance = calculateDistance(vehicleLat,vehicleLon,busStopLat,busStopLon);
            }

            // Si la distancia es menor que el radio de proximidad, devolvemos el paradero
            if (distance <= proximityRadiusM) {
                return busStop; // Devuelve el paradero donde está el vehículo
            }
        }
        return null; // Si no está cerca de ningún paradero
    }
}
