package com.labotec.traccar.app.lib;

import net.sf.geographiclib.Geodesic;
import net.sf.geographiclib.GeodesicData;

import java.util.ArrayList;
import java.util.List;

public class GpsLibDecoded {
    private final List<double[]> coordinates;        // Coordenadas decodificadas de la polilínea
    private final List<double[]> interpolatedRoute;  // Ruta interpolada a una densidad específica
    private final double[] currentPoint;             // Ubicación actual del vehículo
    private final double tolerance;                  // Tolerancia para verificar desviación de la ruta
    private final double maxInterpolationDistance;   // Máxima distancia entre puntos interpolados

    public GpsLibDecoded(List<String> polylines, double[] currentPoint, double tolerance, double maxInterpolationDistance) {
        this.coordinates = new ArrayList<>();
        for (String polyline : polylines) {
            List<double[]> decodedCoordinates = decodePolyline(polyline.replace("\\\\", "\\"));
            this.coordinates.addAll(decodedCoordinates);
            // Imprimir la última coordenada decodificada de la polilínea
            // Verificar si la polilínea tiene coordenadas
            if (!decodedCoordinates.isEmpty()) {
                // Imprimir la primera coordenada
                double[] firstCoordinate = decodedCoordinates.get(0);
                System.out.println("Primera coordenada: Lat = " + firstCoordinate[0] + ", Lng = " + firstCoordinate[1]);

                // Imprimir la última coordenada
                double[] lastCoordinate = decodedCoordinates.get(decodedCoordinates.size() - 1);
                System.out.println("Última coordenada: Lat = " + lastCoordinate[0] + ", Lng = " + lastCoordinate[1]);
            }
        }

        this.currentPoint = currentPoint;
        this.tolerance = tolerance;
        this.maxInterpolationDistance = maxInterpolationDistance;
        this.interpolatedRoute = interpolateRoute(this.coordinates, maxInterpolationDistance);
    }
    // Calcular la distancia total recorrida desde el inicio hasta el punto más cercano al vehículo
    public double getDistanceTraveled() {
        double distanceTraveled = 0.0;
        double minDistance = Double.MAX_VALUE;
        int closestIndex = -1;

        // Encontrar el punto más cercano al vehículo en la ruta interpolada
        for (int i = 0; i < interpolatedRoute.size(); i++) {
            GeodesicData geodesicData = Geodesic.WGS84.Inverse(interpolatedRoute.get(i)[0], interpolatedRoute.get(i)[1], currentPoint[0], currentPoint[1]);
            double distance = geodesicData.s12;
            if (distance < minDistance) {
                minDistance = distance;
                closestIndex = i;
            }
        }

        // Calcular la distancia acumulada desde el inicio hasta el punto más cercano
        for (int i = 0; i < closestIndex; i++) {
            distanceTraveled += Geodesic.WGS84.Inverse(
                    interpolatedRoute.get(i)[0], interpolatedRoute.get(i)[1],
                    interpolatedRoute.get(i + 1)[0], interpolatedRoute.get(i + 1)[1]
            ).s12;
        }

        return distanceTraveled;
    }

    // Función para decodificar una polilínea en una lista de coordenadas
    private List<double[]> decodePolyline(String polyline) {
        List<double[]> coordinates = new ArrayList<>();
        int index = 0, lat = 0, lng = 0;

        while (index < polyline.length()) {
            int result = 0, shift = 0, b;

            // Decodificación de latitud
            do {
                if (index >= polyline.length()) return coordinates;
                b = polyline.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);

            int deltaLat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += deltaLat;

            result = 0;
            shift = 0;

            // Decodificación de longitud
            do {
                if (index >= polyline.length()) return coordinates;
                b = polyline.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);

            int deltaLng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += deltaLng;

            // Agregar coordenadas a la lista
            double latitude = lat / 1E5;
            double longitude = lng / 1E5;
            coordinates.add(new double[]{latitude, longitude});

            // Imprimir las coordenadas decodificadas
        }
        return coordinates;
    }


    // Función para interpolar puntos entre dos coordenadas
    private List<double[]> interpolatePoints(double[] p1, double[] p2, double maxDistance) {
        List<double[]> points = new ArrayList<>();
        GeodesicData geodesicData = Geodesic.WGS84.Inverse(p1[0], p1[1], p2[0], p2[1]);
        double distance = geodesicData.s12;
        points.add(p1);

        if (distance > maxDistance) {
            int numPoints = (int) (distance / maxDistance);
            for (int i = 1; i < numPoints; i++) {
                double fraction = i / (double) (numPoints + 1);
                GeodesicData intermediate = Geodesic.WGS84.Direct(p1[0], p1[1], geodesicData.azi1, fraction * distance);
                points.add(new double[]{intermediate.lat2, intermediate.lon2});
            }
        }

        points.add(p2);
        return points;
    }

    // Función para interpolar una ruta completa
    private List<double[]> interpolateRoute(List<double[]> coordinates, double maxDistance) {
        List<double[]> route = new ArrayList<>();
        for (int i = 0; i < coordinates.size() - 1; i++) {
            route.addAll(interpolatePoints(coordinates.get(i), coordinates.get(i + 1), maxDistance));
        }
        return route;
    }

    // Calcular la distancia total de la ruta interpolada
    public double getTotalDistance() {
        double totalDistance = 0.0;
        for (int i = 0; i < interpolatedRoute.size() - 1; i++) {
            GeodesicData geodesicData = Geodesic.WGS84.Inverse(interpolatedRoute.get(i)[0], interpolatedRoute.get(i)[1],
                    interpolatedRoute.get(i + 1)[0], interpolatedRoute.get(i + 1)[1]);
            totalDistance += geodesicData.s12;
        }
        return totalDistance;
    }

    // Calcular el porcentaje de ruta completado
    public double getCompletionPercentage() {
        double minDistance = Double.MAX_VALUE;
        int closestIndex = -1;

        for (int i = 0; i < interpolatedRoute.size(); i++) {
            GeodesicData geodesicData = Geodesic.WGS84.Inverse(interpolatedRoute.get(i)[0], interpolatedRoute.get(i)[1], currentPoint[0], currentPoint[1]);
            double distance = geodesicData.s12;
            if (distance < minDistance) {
                minDistance = distance;
                closestIndex = i;
            }
        }

        return Math.round((closestIndex / (double) (interpolatedRoute.size() - 1)) * 100 * 100) / 100.0;
    }

    // Verificar si el vehículo está fuera de la ruta
    public boolean isOffRoute() {
        return getDistanceFromRoute() > tolerance;
    }

    // Obtener mensaje de alerta de desviación de ruta
    public String getOffRouteAlert() {
        return isOffRoute() ? "¡Alerta! Estás a más de " + tolerance + " metros de la ruta."
                : "Estás dentro de la tolerancia de " + tolerance + " metros.";
    }

    // Obtener la distancia mínima desde el punto actual hasta la ruta
    public double getDistanceFromRoute() {
        double minDistance = Double.MAX_VALUE;

        for (double[] point : interpolatedRoute) {
            GeodesicData geodesicData = Geodesic.WGS84.Inverse(point[0], point[1], currentPoint[0], currentPoint[1]);
            minDistance = Math.min(minDistance, geodesicData.s12);
        }

        return minDistance;
    }
    public boolean isInGeofence(double[] center, double radius) {
        // Calcula la distancia entre el punto actual y el centro de la geocerca
        GeodesicData geodesicData = Geodesic.WGS84.Inverse(currentPoint[0], currentPoint[1], center[0], center[1]);
        double distance = geodesicData.s12; // Distancia en metros

        // Verifica si la distancia está dentro del radio de la geocerca
        return distance <= radius;
    }

}
