package com.labotec.traccar.app.utils;

public class GeoUtils {

    private static final double EARTH_RADIUS = 6371000; // Radio de la Tierra en metros

    /**
     * Verifica si un punto está dentro de una geocerca.
     *
     * @param lat1  Latitud del centro de la geocerca.
     * @param lon1  Longitud del centro de la geocerca.
     * @param latV  Latitud del punto a verificar.
     * @param lonV  Longitud del punto a verificar.
     * @param radius Radio de la geocerca en metros.
     * @return true si el punto está dentro de la geocerca, false en caso contrario.
     */
    public static boolean isWithinGeofence(double lat1, double lon1, double latV, double lonV, double radius) {
        double deltaLat = Math.toRadians(latV - lat1);
        double deltaLon = Math.toRadians(lonV - lon1);

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(latV))
                * Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS * c;

        return distance <= radius;
    }
}
