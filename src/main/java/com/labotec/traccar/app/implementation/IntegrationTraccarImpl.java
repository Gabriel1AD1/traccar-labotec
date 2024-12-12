package com.labotec.traccar.app.implementation;

import com.labotec.traccar.app.mapper.model.RouteModelMapper;
import com.labotec.traccar.app.ports.input.email.GoogleEmail;
import com.labotec.traccar.app.ports.input.repository.*;
import com.labotec.traccar.app.ports.out.IntegrationTraccarService;
import com.labotec.traccar.domain.database.models.Route;
import com.labotec.traccar.domain.database.models.Schedule;
import com.labotec.traccar.domain.database.models.Vehicle;
import com.labotec.traccar.domain.web.dto.traccar.DeviceRequestDTO;
import com.labotec.traccar.domain.web.dto.traccar.LastedInformationVehicle;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class IntegrationTraccarImpl implements IntegrationTraccarService {

    private final RouteRepository routeRepository;
    private final CompanyRepository companyRepository;
    private final BusStopRepository busStopRepository;
    private final RouteBusStopRepository routeBusStopRepository;
    private final RouteModelMapper routeModelMapper;
    private final OverviewPolylineRepository overviewPolylineRepository;
    private final VehicleRepository vehicleRepository;
    private final ScheduleRepository scheduleRepository;
    private final GoogleEmail googleEmail;
    public String processPosition(LastedInformationVehicle informationVehicle) {
        // 1. Buscar el vehículo usando el deviceId
        Vehicle vehicle = vehicleRepository.findByDevice(Long.valueOf(informationVehicle.getDeviceId()));
        System.out.println(vehicle.toString());

        // 2. Obtener el último registro de la programación
        Schedule schedule = scheduleRepository.findByVehicleLastedRegister(Long.valueOf(informationVehicle.getDeviceId()),1L);
        System.out.println(schedule.toString());

        // 3. Obtener la ruta de la programación
        Route route = schedule.getRoute();
        System.out.println(route.toString());
        /*
        // 4. Obtener la geocerca circular
        CircularGeofence geofence = schedule.getGeofence();


        double[] geofenceCenter = {geofence.getLatitude(), geofence.getLongitude()};
        double geofenceRadius = geofence.getRadius();

        // 5. Obtener la ruta completa (polilíneas)
        List<RouteBusStop> routeBusStops = routeBusStopRepository.findByRouteOrderByOrder(route);
        System.out.println(routeBusStops.toString());
        List<String> polylines = new ArrayList<>();

        for (RouteBusStop busStop : routeBusStops) {
            OverviewPolyline polyline = overviewPolylineRepository.findByRouteBusStopAndPrimary(busStop);
            System.out.println(polyline.getPolyline());
            if (polyline != null) {
                polylines.add(polyline.getPolyline());
            }
        }

        // 6. Obtener la ubicación actual del vehículo
        double[] currentPoint = {informationVehicle.getLatitude(), informationVehicle.getLongitude()};
        double tolerance = TOLERANCE;
        double maxInterpolationDistance = INTERPOLATION_DISTANCE;

        // 7. Evaluar si el vehículo está en la geocerca
        GpsLibDecoded gpsLib = new GpsLibDecoded(polylines, currentPoint, tolerance, maxInterpolationDistance);
        //GpsUtil gpsUtil = new GpsUtil(polylines, currentPoint, tolerance,maxInterpolationDistance);

        boolean isOffGeofence = !gpsLib.isInGeofence(geofenceCenter, geofenceRadius);  // Asegurarse de usar la lógica correcta
        boolean isOffRoute = gpsLib.isOffRoute();

        if (isOffRoute ) {
            String subject = "ALERTA: El vehículo " + vehicle.getLicensePlate() + " está fuera de ruta";
            String body = "El vehículo se ha desviado " + gpsLib.getDistanceFromRoute() +
                    " metros de la ruta programada para el día de hoy: " + route.getName();
            googleEmail.sendEmail(EMAIL_ADDRESS, subject, body);
            System.out.println(body);
        }
        System.out.println("El vehículo se ha desviado " + gpsLib.getDistanceFromRoute() +
                " metros de la ruta programada para el día de hoy: " + route.getName());
        System.out.println("Porcentaje de ruta completado: " + gpsLib.getCompletionPercentage() + "%");
         */
        return "GOOD";


    }

    @Override
    public void processPositionDataStop(DeviceRequestDTO deviceRequestDTO) {

    }
}
