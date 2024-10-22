package com.labotec.traccar.infra.web.controller.rest.traccar;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.operation.distance.DistanceOp;

public class RouteCheck {
    public static void main(String[] args) {
        // Crear ruta como LineString con las coordenadas de la ruta calculada
        GeometryFactory geometryFactory = new GeometryFactory();
        Coordinate[] coordinates = {
                new Coordinate(40.730610, -73.935242),
                new Coordinate(40.740610, -73.945242),
                new Coordinate(40.750610, -73.975242)
        };
        LineString routeLine = geometryFactory.createLineString(coordinates);

        // Ubicación actual del vehículo
        Coordinate vehicleLocation = new Coordinate(40.735610, -73.940242); // Ubicación de prueba
        Point vehiclePoint = geometryFactory.createPoint(vehicleLocation);

        // Verificar si la ubicación está cerca de la ruta
        double distance = DistanceOp.distance(vehiclePoint, routeLine);
        double threshold = 0.001; // Tolerancia en grados (~100 metros)

        if (distance < threshold) {
            System.out.println("El vehículo está siguiendo la ruta.");
        } else {
            System.out.println("El vehículo está fuera de la ruta.");
        }
    }
}
