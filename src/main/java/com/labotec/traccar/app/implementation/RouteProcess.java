package com.labotec.traccar.app.implementation;

import com.labotec.traccar.app.enums.RouteType;
import com.labotec.traccar.app.ports.input.repository.*;
import com.labotec.traccar.app.utils.GeoUtils;
import com.labotec.traccar.domain.database.models.VehiclePosition;
import com.labotec.traccar.domain.database.models.read.InformationRoute;
import com.labotec.traccar.domain.enums.TYPE_BUS_STOP;
import com.labotec.traccar.domain.enums.TYPE_GEOFENCE;
import com.labotec.traccar.domain.query.ScheduleProcessPosition;
import com.labotec.traccar.domain.query.ScheduleRouteBusStopProjection;
import com.labotec.traccar.domain.web.dto.labotec.response.BusStopResponse;
import com.labotec.traccar.domain.web.dto.labotec.response.RouteBusStopSegmentResponse;
import com.labotec.traccar.domain.web.dto.traccar.DeviceRequestDTO;
import com.labotec.traccar.infra.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static com.labotec.traccar.domain.enums.TYPE_BUS_STOP.FINAL;

@AllArgsConstructor
@Service
public class RouteProcess {
    private final BusStopRepository busStopRepository;
    private final ScheduleRepository scheduleRepository;
    private final VehiclePositionRepository vehiclePositionRepository;
    private final VehicleRepository vehicleRepository;
    private final RouteRepository routeRepository;
    private final StopRegisterRepository stopRegisterRepository;
    private final GeofenceCircularRepository geofenceCircularRepository;
    private final StateVehiclePositionRepository stateVehiclePositionRepository;
    private final RouteBusStopResponseSegmentRepository routeBusStopSegmentRepository;
    private static final Logger logger = LoggerFactory.getLogger(RouteProcess.class);
    public void validateRoute(DeviceRequestDTO deviceRequestDTO) {
        // Obtenemos la Velocidad del vehiculo
        double speed = deviceRequestDTO.getSpeed();
        boolean isNotMoving = speed == 0.0;

        // Obtener la hora actual en Instant
        Instant timeCurrent =  getTimeCurrent();
        Double deviceLatitude = deviceRequestDTO.getLatitude();;
        Double deviceLongitude = deviceRequestDTO.getLongitude();
        // Obtener la programación asociada al vehículo y hora actual
        ScheduleProcessPosition scheduleProcessPosition = scheduleRepository.findByScheduleProjectionVehicleIdAndInstantNow(deviceRequestDTO.getDeviceId(), timeCurrent).orElseThrow(() -> new IllegalArgumentException("Programación no encontrada"));
        InformationRoute informationRoute = getInformationRoute(deviceRequestDTO.getDeviceId(),timeCurrent);
        long routeId = informationRoute.getRouteId();
        Long scheduleId  = informationRoute.getScheduleId();
        RouteType routeType = getTypeRoute(routeRepository.getRouteTypeByRouteId(routeId));
        //Si no se esta moviendo pues se actualiza el tiempo parado
        if (isNotMoving){
            vehiclePositionRepository.updateTimeStopByRouteId(scheduleId,deviceLatitude,deviceLongitude,timeCurrent);
        }
        VehiclePosition vehiclePosition = vehiclePositionRepository.findByScheduleId(scheduleId);
        Long idVehiclePosition = vehiclePosition.getId();
        Long lasteIdBusStop = vehiclePosition.getCurrentBusStop();
        logger.info("Pocision del vehiculo relacionado ala programacion {}",vehiclePosition);
        //Nueva generacion de busqueda
        List<RouteBusStopSegmentResponse> busStopSegments = getSegmentBusStop(routeId);
        logger.info("Obteniendo las ruta del vehiculo {} ", busStopSegments);
        //Obtener la lista de paraderos del vehiculo;
        List<BusStopResponse> getListBusStop = getBusStops(busStopSegments);
        //Obtener el paradero actual del vehiculo
        BusStopResponse currentBusStop = getCurrentBusStop(deviceRequestDTO.getLatitude(), deviceRequestDTO.getLongitude(),getListBusStop,routeType);
        //Obtenemos el id del paradero de inicio  ;
        Long firstBusStopId  = getFirstBusStopId(busStopSegments);
        //Obtenemos el id del paradero final
        Long finalBusStopId = getLastBusStopId(busStopSegments);
        //Obtenemos el id del paradero
        BusStopResponse firstBusStop = getBusStopById(getListBusStop,firstBusStopId);
        //Obtenemos el ultimo Paradero
        BusStopResponse finalBusStop = getBusStopById(getListBusStop,finalBusStopId);



        logger.info("Ulitmo paradero {} " ,finalBusStop);
        boolean isNullBusStop = currentBusStop == null;
        //EN PARADERO
        if (!isNullBusStop){


            Long currentBusStopId = currentBusStop.getId();



            // Procesar ubicación del vehículo en el primer paradero
            boolean isAtFirstBusStop = GeoUtils.isWithinGeofence(firstBusStop.getLatitude(), firstBusStop.getLongitude(), deviceRequestDTO.getLatitude(), deviceRequestDTO.getLongitude(), 40);

            // Procesar ubicación del vehículo en el último paradero
            boolean isAtLastBusStop = GeoUtils.isWithinGeofence(finalBusStop.getLatitude(), finalBusStop.getLongitude(), deviceRequestDTO.getLatitude(), deviceRequestDTO.getLongitude(), 40);
            //Obtenemos el segmento de donde esta parado el auto
            RouteBusStopSegmentResponse currentSegment = getBusStopSegmentById(currentBusStopId,busStopSegments);
            //Verificamos que el tipo de bus stop donde esta parado sea el final
            Long currentOrdenSegment = currentSegment.getOrder();
            Integer minWaitTime = currentSegment.getMinWaitTime();
            Integer maxWaitTime = currentSegment.getMaxWaitTime();
            Instant nextMaxBusStopTimeBusStop = Instant.now().plus(currentSegment.getMaxWaitTime().longValue(), ChronoUnit.MINUTES)
                    .plus(currentSegment.getEstimatedTravelTime().longValue(), ChronoUnit.MINUTES);
            Instant nextMinBusStopTimeBusStop = Instant.now().plus(currentSegment.getMinWaitTime().longValue(), ChronoUnit.MINUTES)
                    .plus(currentSegment.getEstimatedTravelTime().longValue(), ChronoUnit.MINUTES);
            if (!vehiclePosition.isResetRoute()){
                if (!vehiclePosition.isCompleteRoute()){
                    // Verificar si el tiempo actual (timeCurrent) está dentro del rango entre el tiempo mínimo y máximo de parada
                    boolean arriveOnTime = !timeCurrent.isBefore(vehiclePosition.getNextMinBusStopTimeBusStop())
                            && !timeCurrent.isAfter(vehiclePosition.getNextMaxBusStopTimeBusStop());
                    //Obtenemos el id del paradero actual
                    // Si el tiempo está dentro del rango de la parada (inclusive)
                    if (!isAtFirstBusStop || !isAtLastBusStop){
                        if (arriveOnTime) {
                            timeComplete(scheduleId,currentBusStopId,true);
                        }

                        // Si el tiempo no está dentro del rango de la parada
                        if (!arriveOnTime) {
                            // Lógica que se ejecuta si el tiempo no está dentro del rango permitido
                            noTimeComplete(scheduleId, currentBusStopId, false);
                        }

                    }
                }

            }


            if (!(currentSegment.getTypeBusStop() == FINAL)){
                Long nexOrderSegment = currentSegment.getOrder() + 1;
                RouteBusStopSegmentResponse nextSegment = getBusStopSegmentByOrder(nexOrderSegment , busStopSegments);
                Long nextBusStopId = nextSegment.getBusStopId();
                //Siguiente paradero
                //Verificamos el paradero si esta parado mucho tiempo sobre el mismo paradero
                //Basicamente revisar si cumple con las horas que se ha parado
                if (!vehiclePosition.isResetRoute()){
                    boolean remainsAtTheSameLocation = isRemainsAtTheSameLocation(currentBusStopId,lasteIdBusStop);
                    if (remainsAtTheSameLocation){
                        int timeWaited = getTimeExceded(vehiclePosition.getCurrentTimeStoppedForBusStop());
                        boolean isLastedPosition = checkMaxWaitTime(vehiclePosition.getMaxWaitTimeForBusStop(),timeWaited);
                        logger.info("TIEMPO EXECEDIDO : {}", timeWaited);
                        if (isLastedPosition){
                            stopRegisterRepository.updateMaxTimeExcess(scheduleId,vehiclePosition.getCurrentBusStop(),timeWaited);
                            stopRegisterRepository.updateAlertSend(scheduleId,vehiclePosition.getCurrentBusStop(),true);
                            stopRegisterRepository.updateTimeExceeded(scheduleId,vehiclePosition.getCurrentBusStop(),true);
                            sendAlertForExceedingWaitTimeForSegment(currentBusStop.getName(),currentBusStopId);
                        }
                    }
                    if(!remainsAtTheSameLocation){
                        int timeWaited = getTimeExceded(vehiclePosition.getCurrentTimeStoppedForBusStop());
                        boolean haveYouLeftTheBusStop = checkMinWaitTime(vehiclePosition.getMinWaitTimeForBusStop(),timeWaited);
                        if (haveYouLeftTheBusStop){
                            stopRegisterRepository.updateMinTimeShortfall(scheduleId,vehiclePosition.getCurrentBusStop(),timeWaited);
                            stopRegisterRepository.updateMinimumTimeMet(scheduleId,vehiclePosition.getCurrentBusStop(),false);
                            stopRegisterRepository.updateAlertSend(scheduleId,vehiclePosition.getCurrentBusStop(),true);
                            sendAlertForExceedingWaitTimeForSegment("EL VEHICULO HA SALIDO ANTES DEL TIEMPO DEL PARADERO QUE SE LAESTIPULADO" , finalBusStopId);
                        }
                    }
                }

                //Paradero inicial
                if (isAtFirstBusStop || currentSegment.getTypeBusStop()==TYPE_BUS_STOP.INICIO) {
                    if (vehiclePosition.isResetRoute()) {
                        stopRegisterRepository.updateEntryTimeForRegisterBuStop(scheduleId,currentBusStopId,Instant.now());
                        scheduleRepository.updateDepartureTime(scheduleProcessPosition.getId(), Instant.now());
                        updateInitialBusStopForVehicleState(
                                currentBusStopId,
                                nextBusStopId,
                                deviceLatitude,
                                deviceLongitude,
                                minWaitTime,
                                maxWaitTime,
                                timeCurrent,
                                currentOrdenSegment,
                                true,
                                false,
                                nextMaxBusStopTimeBusStop,
                                nextMinBusStopTimeBusStop,
                                idVehiclePosition
                        );
                    }


                }
                //Paraderos intermedios
                if (!isAtLastBusStop && !isAtFirstBusStop || currentSegment.getTypeBusStop() == TYPE_BUS_STOP.INTERMEDIO){

                    stopRegisterRepository.updateEntryTimeForRegisterBuStop(scheduleId,currentBusStopId,Instant.now());
                    updateInitialBusStopForVehicleState(
                            currentBusStopId,
                            nextBusStopId,
                            deviceLatitude,
                            deviceLongitude,
                            minWaitTime,
                            maxWaitTime,
                            timeCurrent,
                            currentOrdenSegment,
                            true,
                            false,
                            nextMaxBusStopTimeBusStop,
                            nextMinBusStopTimeBusStop,
                            idVehiclePosition
                    );
                }
            }


            //Padero final
            if (isAtLastBusStop || currentSegment.getTypeBusStop() == FINAL){
                stopRegisterRepository.updateEntryTimeForRegisterBuStop(scheduleId,currentBusStopId,Instant.now());
                updateInitialBusStopForVehicleState(
                        currentBusStopId,
                        null,
                        deviceLatitude,
                        deviceLongitude,
                        minWaitTime,
                        maxWaitTime,
                        timeCurrent,
                        currentOrdenSegment,
                        true,
                        false,
                        null,
                        null,
                        idVehiclePosition
                );
                vehiclePositionRepository.updateCompleteRouteById(idVehiclePosition,true);
                scheduleRepository.updateArrivedTime(scheduleProcessPosition.getId(),Instant.now());
            }

        }
        //No se encuentra en un paradero si no se encuentra bien en la ruta de punto a ----------- b
        //O bien no se encuentra en la ruta  simplemente esto verifica que el vehiculo no sea igual

        if (!vehiclePosition.isResetRoute()){
            if (isNullBusStop){
                //Actualizar la pocision dle vehiculo
                vehiclePosition.setWhereaboutsStatus(false);
                vehiclePositionRepository.save(vehiclePosition);
                scheduleRepository.updateProgramCompletionStatus(scheduleId,true);

                boolean isBusStop = vehiclePosition.isWhereaboutsStatus();
                if (!isBusStop){
                    stopRegisterRepository.updateExitTimeForRegisterBusStop(scheduleId,vehiclePosition.getCurrentBusStop(), Instant.now());
                    int timeWaited = getTimeExceded(vehiclePosition.getCurrentTimeStoppedForBusStop());
                    boolean haveYouLeftTheBusStop = checkMinWaitTime(vehiclePosition.getMinWaitTimeForBusStop(),timeWaited);


                    if (haveYouLeftTheBusStop){
                        stopRegisterRepository.updateMinTimeShortfall(scheduleId,vehiclePosition.getCurrentBusStop(),timeWaited);
                        stopRegisterRepository.updateMinimumTimeMet(scheduleId, vehiclePosition.getCurrentBusStop(),false);
                        stopRegisterRepository.updateAlertSend(scheduleId,vehiclePosition.getCurrentBusStop(),true);
                        sendAlertForExceedingWaitTimeForSegment("EL VEHICULO HA SALIDO ANTES DEL TIEMPO DEL PARADERO QUE SE LAESTIPULADO" , 2);
                    }
                    logger.warn("no se encuentra en un parametro");
                }
            }
        }




        // Obtener el tipo de geocerca de la programación
        TYPE_GEOFENCE geofenceType = scheduleProcessPosition.getTypeGeofence();

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

    private void noTimeComplete(Long scheduleId, Long currentBusStopId, boolean b) {
        logger.warn("TIEMPO NO COMPLETADO D:");
    }

    private void timeComplete(Long scheduleId, Long currentBusStopId, boolean b) {
        logger.info("TIEMPO COMPLETADO :D");
    }

    private RouteType getTypeRoute(Optional<RouteType> routeTypeByRouteId) {
        logger.info("Buscando el tipo de ruta");
        return routeTypeByRouteId.orElseThrow(
                () -> new EntityNotFoundException("La entidad no ha sido encontrada")
        );
    }


    public RouteBusStopSegmentResponse getBusStopSegmentByOrder(Long order, List<RouteBusStopSegmentResponse> busStopSegments) {
        return busStopSegments.stream()
                .filter(segment -> segment.getOrder().equals(order))  // Filtra por el campo 'order'
                .findFirst()  // Devuelve el primer resultado que cumpla con la condición
                .orElse(null); // Si no se encuentra, devuelve null
    }

    private boolean isRemainsAtTheSameLocation(Long currentBusStopId, Long lasteIdBusStop) {
        return Objects.equals(currentBusStopId, lasteIdBusStop);
    }
    private RouteBusStopSegmentResponse getBusStopSegmentById(Long busStopId, List<RouteBusStopSegmentResponse> busStopSegments) {
        return busStopSegments.stream()
                .filter(segment -> segment.getBusStopId().equals(busStopId))  // Filtra por busStopId
                .findFirst()  // Devuelve el primer resultado que cumpla la condición
                .orElse(null); // Si no se encuentra, devuelve null
    }

    // Método para buscar un paradero por su id
    private BusStopResponse getBusStopById(List<BusStopResponse> busStopList, Long busStopId) {
        return busStopList.stream()
                .filter(busStop -> busStop.getId().equals(busStopId))  // Filtra por id
                .findFirst()  // Obtiene el primer paradero que coincida
                .orElse(null);  // Devuelve null si no se encuentra
    }
    // Método para obtener la lista de BusStopResponse a partir de los BusStopIds
    public List<BusStopResponse> getBusStops(List<RouteBusStopSegmentResponse> busStopSegments) {
        List<BusStopResponse> busStopList = new ArrayList<>();

        busStopSegments.forEach(s -> {
            BusStopResponse busStopResponse = busStopRepository.findByResourceId(s.getBusStopId());
            if (busStopResponse != null) {
                busStopList.add(busStopResponse);
            }
        });

        return busStopList;
    }

    private InformationRoute getInformationRoute(long deviceId, Instant now) {
        return scheduleRepository.findByInformationSchedule(deviceId,now).orElseThrow(
                () -> new EntityNotFoundException("Informaicon no encontrada")
        );
    }

    private Instant getTimeCurrent() {
        ZoneId localZoneId = ZoneId.systemDefault();
        ZonedDateTime localDateTime = ZonedDateTime.now(localZoneId);
        System.out.println("Hora local: " + localDateTime);
        System.out.println("Local instant: " + Instant.now());
        return Instant.now();
    }

    private List<RouteBusStopSegmentResponse> getSegmentBusStop(Long routeId){
        return routeBusStopSegmentRepository.getSegmentsByRouteId(routeId);
    }

    // Enviar alerta si se excede el tiempo máximo de espera
    private void sendAlertForExceedingWaitTimeForSegment(String busStop, long timeSpent) {
        // Lógica para enviar alerta (por ejemplo, log o notificación)
        logger.warn("AlertaA: El vehículo ha excedido el tiempo de espera en {}. Tiempo transcurrido: {} minutos.", busStop, timeSpent);
    }
    private BusStopResponse getCurrentBusStop(Double vehicleLatitude,Double vehicleLongitude,List<BusStopResponse> listBusStop , RouteType type){
        double radiusApplicateForBusStop = 30.0;
        return GeoUtils.getBusStopResponseIdIfWithinProximity(vehicleLatitude,vehicleLongitude,listBusStop,radiusApplicateForBusStop, type);
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
    // Método para obtener el primer ID de paradero por el campo 'order'
    public static Long getFirstBusStopId(List<RouteBusStopSegmentResponse> routeBusStops) {
        // Verificar si la lista no está vacía
        if (routeBusStops != null && !routeBusStops.isEmpty()) {
            // Ordenar la lista por el campo 'order' (de menor a mayor)
            Collections.sort(routeBusStops, Comparator.comparingLong(RouteBusStopSegmentResponse::getOrder));

            // Obtener el primer paradero
            RouteBusStopSegmentResponse firstBusStop = routeBusStops.get(0);
            return firstBusStop.getBusStopId(); // Devuelve el ID del primer paradero
        } else {
            logger.warn("La lista de paraderos está vacía.");
            return null;
        }
    }
    // Método para obtener el último ID de paradero por el campo 'order'
    public static Long getLastBusStopId(List<RouteBusStopSegmentResponse> routeBusStops) {
        // Verificar si la lista no está vacía
        if (routeBusStops != null && !routeBusStops.isEmpty()) {
            // Ordenar la lista por el campo 'order' (de menor a mayor)
            Collections.sort(routeBusStops, Comparator.comparingLong(RouteBusStopSegmentResponse::getOrder));

            // Obtener el último paradero
            RouteBusStopSegmentResponse lastBusStop = routeBusStops.get(routeBusStops.size() - 1);
            return lastBusStop.getBusStopId(); // Devuelve el ID del último paradero
        } else {
            logger.warn("La lista de paraderos está vacía.");
            return null;
        }
    }
    public ScheduleRouteBusStopProjection findSegmentByOrder(List<ScheduleRouteBusStopProjection> segments, Long order) {
        // Buscar el segmento por el orden
        return segments.stream()
                .filter(segment -> segment.getOrder() != null && segment.getOrder().equals(order))
                .findFirst()
                .orElse(null);
    }
    private void updateInitialBusStopForVehicleState(
            Long currentBusStopId,
            Long nextBusStopId,
            Double latitude,
            Double longitude,
            Integer minWaitTime,
            Integer maxWaitTime,
            Instant currentTimeStoppedForBusStop,
            Long currentSegmentOrder,
            Boolean whereaboutsStatus,
            Boolean resetRoute,
            Instant nextMaxBusStopTimeBusStop,
            Instant nextMinBusStopTimeBusStop,
            Long id
    ){
        vehiclePositionRepository.updateInformationInitialVehicleByIdIfSegmentChanged(currentBusStopId,
                nextBusStopId,
                latitude,
                longitude,
                minWaitTime,
                maxWaitTime,
                currentTimeStoppedForBusStop,
                currentSegmentOrder,
                whereaboutsStatus,
                resetRoute,
                nextMaxBusStopTimeBusStop,
                nextMinBusStopTimeBusStop,
                id);
    }
    public static boolean checkMaxWaitTime(Integer maxWaitTime , Integer timeWaited) {
        // Verificar si está dentro del rango
        return timeWaited > maxWaitTime;
    }
    public static int getTimeExceded(Instant arrivalTime){
        Instant currentTime = Instant.now();
        return (int) ChronoUnit.MINUTES.between(arrivalTime, currentTime);
    }
    public static boolean checkMinWaitTime(Integer minWaitTime , int timeWaited) {
        return timeWaited < minWaitTime;
    }
}
