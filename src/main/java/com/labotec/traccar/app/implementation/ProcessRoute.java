package com.labotec.traccar.app.implementation;

import com.labotec.traccar.app.ports.input.repository.*;
import com.labotec.traccar.app.ports.out.ProcessRouteService;
import com.labotec.traccar.app.utils.GeoUtils;
import com.labotec.traccar.domain.database.models.*;
import com.labotec.traccar.domain.enums.STATE_VEHICLE;
import com.labotec.traccar.domain.enums.TYPE_GEOFENCE;
import com.labotec.traccar.domain.query.ScheduleBusStopProjection;
import com.labotec.traccar.domain.query.ScheduleProcessPosition;
import com.labotec.traccar.domain.query.ScheduleRouteBusStopProjection;
import com.labotec.traccar.domain.query.ScheduleRouteProjection;
import com.labotec.traccar.domain.web.dto.traccar.DeviceRequestDTO;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
public class ProcessRoute implements ProcessRouteService {
    private final ScheduleRepository scheduleRepository;
    private final VehicleRepository vehicleRepository;
    private final RouteRepository routeRepository;
    private final GeofenceCircularRepository geofenceCircularRepository;
    private final StateVehiclePositionRepository stateVehiclePositionRepository;

    @Override
    public void validateRoute(DeviceRequestDTO deviceRequestDTO) {
        //Obtenemos la Velocidad del vehiculo
        double speed = deviceRequestDTO.getSpeed();

        boolean isMoving = speed > 0.0;

        // Obtener la hora actual en Instant
        Instant now = Instant.now();
        ZoneId localZoneId = ZoneId.systemDefault();
        ZonedDateTime localDateTime = ZonedDateTime.now(localZoneId);
        System.out.println("Hora local: " + localDateTime);
        System.out.println("Local instant: " + now);


        // Obtener la última posición del vehículo
        StateVehiclePosition lastedPosition = stateVehiclePositionRepository.findByTraccarDeviceId(deviceRequestDTO.getDeviceId())
                .orElseThrow(() -> new IllegalArgumentException("La posición no existe, revisar"));

        // Obtener la programación asociada al vehículo y hora actual
        ScheduleProcessPosition scheduleProcessPosition = scheduleRepository.findByScheduleProjectionVehicleIdAndInstantNow(deviceRequestDTO.getDeviceId(), now)
                .orElseThrow(() -> new IllegalArgumentException("Programación no encontrada"));

        // Obtener la ruta asociada a la programación
        ScheduleRouteProjection routeForSchedule = scheduleProcessPosition.getRoute();

        // Obtener los segmentos de la ruta
        List<ScheduleRouteBusStopProjection> segments = routeForSchedule.getBusStopsList();

        // Obtener el tipo de geocerca de la programación
        TYPE_GEOFENCE geofenceType = scheduleProcessPosition.getTypeGeofence();

        // Segmento inicial de la ruta
        ScheduleRouteBusStopProjection initialSegment = segments.stream()
                .filter(segment -> segment.getOrder() != null && segment.getOrder().equals(1L))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No existe el segmento inicial"));

        // Segmento final de la ruta
        ScheduleRouteBusStopProjection finalSegment = segments.stream()
                .filter(segment -> segment.getOrder() != null)
                .max(Comparator.comparingLong(ScheduleRouteBusStopProjection::getOrder))
                .orElseThrow(() -> new IllegalArgumentException("No existen segmentos válidos"));

        // Obtener el primer y último paradero
        ScheduleBusStopProjection firstBusStop = initialSegment.getFirstBusStop();
        ScheduleBusStopProjection lastBusStop = finalSegment.getSecondBusStop();

        if (!isMoving){
            // Procesar ubicación del vehículo en el primer paradero
            boolean isAtFirstBusStop = GeoUtils.isWithinGeofence(
                    Double.parseDouble(firstBusStop.getLatitude()),
                    Double.parseDouble(firstBusStop.getLongitude()),
                    deviceRequestDTO.getLatitude(),
                    deviceRequestDTO.getLongitude(),
                    15
            );

            // Procesar ubicación del vehículo en el último paradero
            boolean isAtLastBusStop = GeoUtils.isWithinGeofence(
                    Double.parseDouble(lastBusStop.getLatitude()),
                    Double.parseDouble(lastBusStop.getLongitude()),
                    deviceRequestDTO.getLatitude(),
                    deviceRequestDTO.getLongitude(),
                    15
            );
            // Si el vehículo está en el primer paradero, actualizar la programación
            if (isAtFirstBusStop) {
                scheduleRepository.updateDepartureTime(scheduleProcessPosition.getId(), now);
                lastedPosition.setLatitude(deviceRequestDTO.getLatitude());
                lastedPosition.setLongitude(deviceRequestDTO.getLongitude());
                lastedPosition.setStateVehicle(STATE_VEHICLE.DETENIDO);
                lastedPosition.setTimeInstantStopped(Instant.now());
                stateVehiclePositionRepository.create(lastedPosition);
            }
            //Vehiculo en medio
            if (!isAtFirstBusStop || !isAtLastBusStop){

            }
            // Si el vehículo está en el último paradero, actualizar la programación
            if (isAtLastBusStop) {
                scheduleRepository.updateArrivedTime(scheduleProcessPosition.getId(), now);
                lastedPosition.setLatitude(deviceRequestDTO.getLatitude());
                lastedPosition.setLongitude(deviceRequestDTO.getLongitude());
                lastedPosition.setStateVehicle(STATE_VEHICLE.DETENIDO);
                lastedPosition.setTimeInstantStopped(Instant.now());
                stateVehiclePositionRepository.create(lastedPosition);
            }



        }


        // Manejar el tipo de geocerca
        switch (geofenceType) {
            case CIRCULAR -> handlerGeofenceCircular(deviceRequestDTO, scheduleProcessPosition);
            case CUADRANGULAR -> handlerGeofenceCuadrangular(deviceRequestDTO, scheduleProcessPosition);
            case POLIGONAL -> handlerGeofencePoligonal(deviceRequestDTO, scheduleProcessPosition);
        }

        // Validar ruta explícitamente si está configurado
        if (scheduleProcessPosition.getValidateRouteExplicit()) {
            // TODO: Implementar validación de polilíneas
        }


    }

    // Manejador para geocerca circular
    private void handlerGeofenceCircular(DeviceRequestDTO deviceRequestDTO, ScheduleProcessPosition schedule) {
        // TODO: Implementar manejo de geocerca circular
    }

    // Manejador para geocerca cuadrangular
    private void handlerGeofenceCuadrangular(DeviceRequestDTO deviceRequestDTO, ScheduleProcessPosition schedule) {
        // TODO: Implementar manejo de geocerca cuadrangular
    }

    // Manejador para geocerca poligonal
    private void handlerGeofencePoligonal(DeviceRequestDTO deviceRequestDTO, ScheduleProcessPosition schedule) {
        // TODO: Implementar manejo de geocerca poligonal
    }
}
