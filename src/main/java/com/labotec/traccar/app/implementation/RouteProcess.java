package com.labotec.traccar.app.implementation;

import com.labotec.traccar.TraccarApplication;
import com.labotec.traccar.app.enums.RouteType;
import com.labotec.traccar.app.lib.GpsLibDecoded;
import com.labotec.traccar.app.ports.input.notification.NotificationTraccar;
import com.labotec.traccar.app.ports.input.repository.*;
import com.labotec.traccar.app.utils.GeoUtils;
import com.labotec.traccar.app.utils.JsonUtils;
import com.labotec.traccar.domain.database.models.Alert;
import com.labotec.traccar.domain.database.models.VehiclePosition;
import com.labotec.traccar.domain.database.models.read.InformationRoute;
import com.labotec.traccar.domain.enums.AlertType;
import com.labotec.traccar.domain.enums.TypeGeofence;
import com.labotec.traccar.domain.query.OptimizedOverviewPolyline;
import com.labotec.traccar.domain.query.OptimizedSchedule;
import com.labotec.traccar.domain.query.ScheduleRouteBusStopProjection;
import com.labotec.traccar.domain.web.dto.labotec.notify.NotificationDTO;
import com.labotec.traccar.domain.web.dto.labotec.response.BusStopResponse;
import com.labotec.traccar.domain.web.dto.labotec.response.ResponseAlert;
import com.labotec.traccar.domain.web.dto.labotec.response.ResponseCircularGeofence;
import com.labotec.traccar.domain.web.dto.labotec.response.ResponseRouteBusStopSegment;
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

import static com.labotec.traccar.app.constants.DeviceConstant.ENGINE;
import static com.labotec.traccar.domain.enums.AlertType.GEOFENCE_VIOLATION;
import static com.labotec.traccar.domain.enums.AlertType.TIME_NOT_COMPLETED;
import static com.labotec.traccar.domain.enums.TypeBusStop.*;

@AllArgsConstructor
@Service
public class RouteProcess {
    private final NotificationTraccar notificationTraccar;
    private final BusStopRepository busStopRepository;
    private final ScheduleRepository scheduleRepository;
    private final VehiclePositionRepository vehiclePositionRepository;
    private final VehicleRepository vehicleRepository;
    private final OverviewPolylineRepository overviewPolylineRepository;
    private final RouteRepository routeRepository;
    private final StopRegisterRepository stopRegisterRepository;
    private final GeofenceCircularRepository geofenceCircularRepository;
    private final StateVehiclePositionRepository stateVehiclePositionRepository;
    private final RouteBusStopResponseSegmentRepository routeBusStopSegmentRepository;
    private final ConfigurationRouteProcessServerRepository configurationRouteProcessServerRepository;
    private static final Logger logger = LoggerFactory.getLogger(RouteProcess.class);
    private final AlertRepository alertRepository;
    private final TraccarApplication traccarApplication;

    public void validateRoute(DeviceRequestDTO deviceRequestDTO) {
        int engine = (int) deviceRequestDTO.getAttributes().get(ENGINE);
        boolean onEngine = engine == 1;
        if (!onEngine){
            return;
        }
        Double validateRadiusBusStopRoute = configurationRouteProcessServerRepository.getConfiguration().getRadiusValidateBusStop();
        // Obtenemos la Velocidad del vehiculo
        double speed = deviceRequestDTO.getSpeed();
        boolean isNotMoving = speed == 0.0;
        // Obtener la hora actual en Instant
        Instant timeCurrent =  getTimeCurrent();
        Double deviceLatitude = deviceRequestDTO.getLatitude();
        Double deviceLongitude = deviceRequestDTO.getLongitude();
        // Obtener la programación asociada al vehículo y hora actual
        OptimizedSchedule optimizedSchedule = scheduleRepository.findDeviceIdAndCurrentTime(deviceRequestDTO.getDeviceId(),timeCurrent);
        InformationRoute informationRoute = getInformationRoute(deviceRequestDTO.getDeviceId(),timeCurrent);
        Long userId = informationRoute.getUserId();
        Long radiusValidatePolyline = optimizedSchedule.getRadiusValidatePolyline();
        Long routeId = informationRoute.getRouteId();
        Long scheduleId  = informationRoute.getScheduleId();
        List<OptimizedOverviewPolyline> overviewPolylines = overviewPolylineRepository.findPrimaryPolylinesByRouteId(routeId);
        List<String> listOverViewPolylines = overviewPolylines.stream()
                .map(OptimizedOverviewPolyline::getPolyline)
                .toList();

        RouteType routeType = getTypeRoute(routeRepository.getRouteTypeByRouteId(routeId));

        //Si no se esta moviendo pues se actualiza el tiempo parado
        if (isNotMoving){
            vehiclePositionRepository.updateTimeStopByRouteId(scheduleId,deviceLatitude,deviceLongitude,timeCurrent);
        }
        VehiclePosition vehiclePosition = vehiclePositionRepository.findByScheduleId(scheduleId);
        Long deviceId = vehiclePosition.getDeviceId();
        Long idVehiclePosition = vehiclePosition.getId();
        Long lasteIdBusStop = vehiclePosition.getCurrentBusStop();
        logger.info("Pocision del vehiculo relacionado ala programacion {}",vehiclePosition);
        //Nueva generacion de busqueda
        List<ResponseRouteBusStopSegment> busStopSegments = getSegmentBusStop(routeId);
        logger.info("Obteniendo las ruta del vehiculo {} ", busStopSegments);
        //Obtener la lista de paraderos del vehiculo;
        List<BusStopResponse> getListBusStop = getBusStops(busStopSegments);
        //Obtener el paradero actual del vehiculo
        BusStopResponse currentBusStop = getCurrentBusStop(validateRadiusBusStopRoute,deviceRequestDTO.getLatitude(), deviceRequestDTO.getLongitude(),getListBusStop,routeType);
        //Obtenemos el id del paradero de inicio  ;
        Long firstBusStopId  = getFirstBusStopId(busStopSegments);
        //Obtenemos el id del paradero final
        Long finalBusStopId = getLastBusStopId(busStopSegments);
        //Obtenemos el id del paradero
        BusStopResponse firstBusStop = getBusStopById(getListBusStop,firstBusStopId);
        //Obtenemos el ultimo Paradero
        BusStopResponse finalBusStop = getBusStopById(getListBusStop,finalBusStopId);
        logger.info("Ulitmo paradero {} " ,finalBusStop);
        double[] position = {deviceLatitude,deviceLongitude};
        GpsLibDecoded gpsLibDecoded = new GpsLibDecoded(listOverViewPolylines,position,radiusValidatePolyline,5.0);

        boolean isNullBusStop = currentBusStop == null;
        //EN PARADERO
        if (!isNullBusStop){
            Double percentageCompleted = gpsLibDecoded.getCompletionPercentage();
            Long currentBusStopId = currentBusStop.getId();
            scheduleRepository.updateCompletionPercentageByScheduleId(scheduleId,percentageCompleted);
            // Procesar ubicación del vehículo en el primer paradero
            boolean isAtFirstBusStop = GeoUtils.isWithinGeofence(firstBusStop.getLatitude(), firstBusStop.getLongitude(), deviceRequestDTO.getLatitude(), deviceRequestDTO.getLongitude(), validateRadiusBusStopRoute);

            // Procesar ubicación del vehículo en el último paradero
            boolean isAtLastBusStop = GeoUtils.isWithinGeofence(finalBusStop.getLatitude(), finalBusStop.getLongitude(), deviceRequestDTO.getLatitude(), deviceRequestDTO.getLongitude(), validateRadiusBusStopRoute);
            //Obtenemos el segmento de donde esta parado el auto
            ResponseRouteBusStopSegment currentSegment = getBusStopSegmentById(currentBusStopId,busStopSegments);
            //Verificamos que el tipo de bus stop donde esta parado sea el final
            Long currentOrdenSegment = currentSegment.getOrder();
            Integer minWaitTime = currentSegment.getMinWaitTime();
            Integer maxWaitTime = currentSegment.getMaxWaitTime();
            Instant nextMaxBusStopTimeBusStop = Instant.now().plus(currentSegment.getMaxWaitTime().longValue(), ChronoUnit.MINUTES).plus(currentSegment.getEstimatedTravelTime().longValue(), ChronoUnit.MINUTES);
            Instant nextMinBusStopTimeBusStop = Instant.now().plus(currentSegment.getMinWaitTime().longValue(), ChronoUnit.MINUTES).plus(currentSegment.getEstimatedTravelTime().longValue(), ChronoUnit.MINUTES);
            if (!vehiclePosition.isResetRoute()){
                if (!vehiclePosition.isCompleteRoute()){
                    // Verificar si el tiempo actual (timeCurrent) está dentro del rango entre el tiempo mínimo y máximo de parada
                    boolean arriveOnTime = !timeCurrent.isBefore(vehiclePosition.getNextMinBusStopTimeBusStop())
                            && !timeCurrent.isAfter(vehiclePosition.getNextMaxBusStopTimeBusStop());
                    //Obtenemos el id del paradero actual
                    // Si el tiempo está dentro del rango de la parada (inclusive)
                    if (!isAtFirstBusStop || !isAtLastBusStop){
                        if (arriveOnTime) {
                            timeComplete(userId,scheduleId);
                        }

                        // Si el tiempo no está dentro del rango de la parada
                        if (!arriveOnTime) {
                            // Lógica que se ejecuta si el tiempo no está dentro del rango permitido
                            noTimeComplete(userId,deviceId,deviceLatitude,deviceLongitude);
                        }

                    }
                }

            }

            if (!(currentSegment.getTypeBusStop() == FINAL)){
                Long nexOrderSegment = currentSegment.getOrder() + 1;
                ResponseRouteBusStopSegment nextSegment = getBusStopSegmentByOrder(nexOrderSegment , busStopSegments);
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
                            sendAlertForExceedingWaitTimeForSegment(userId,deviceId, (long) timeWaited,deviceLatitude,deviceLongitude);
                        }
                    }
                    if(!remainsAtTheSameLocation){
                        int timeWaited = getTimeExceded(vehiclePosition.getCurrentTimeStoppedForBusStop());
                        boolean haveYouLeftTheBusStop = checkMinWaitTime(vehiclePosition.getMinWaitTimeForBusStop(),timeWaited);
                        if (haveYouLeftTheBusStop){
                            stopRegisterRepository.updateMinTimeShortfall(scheduleId,vehiclePosition.getCurrentBusStop(),timeWaited);
                            stopRegisterRepository.updateMinimumTimeMet(scheduleId,vehiclePosition.getCurrentBusStop(),false);
                            stopRegisterRepository.updateAlertSend(scheduleId,vehiclePosition.getCurrentBusStop(),true);
                            sendAlertForExceedingWaitTimeForSegment(userId,deviceId, (long) timeWaited,deviceLatitude,deviceLongitude);
                        }
                    }
                }

                //Paradero inicial
                if (isAtFirstBusStop || currentSegment.getTypeBusStop()== INICIO) {
                    if (vehiclePosition.isResetRoute()) {
                        stopRegisterRepository.updateEntryTimeForRegisterBuStop(scheduleId,currentBusStopId,Instant.now());
                        scheduleRepository.updateDepartureTime(scheduleId, Instant.now());
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
                if (!isAtLastBusStop && !isAtFirstBusStop || currentSegment.getTypeBusStop() == INTERMEDIO){

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
                scheduleRepository.updateArrivedTime(scheduleId,Instant.now());
            }

        }
        //No se encuentra en un paradero si no se encuentra bien en la ruta de punto a ----------- b
        //O bien no se encuentra en la ruta  simplemente esto verifica que el vehiculo no sea igual

        if (!vehiclePosition.isResetRoute()){
            if (isNullBusStop){
                boolean isFinalBusStop= Objects.equals(vehiclePosition.getCurrentBusStop(), finalBusStopId);
                if (isFinalBusStop){
                    //Actualizar la pocision dle vehiculo
                    vehiclePosition.setWhereaboutsStatus(false);
                    vehiclePositionRepository.save(vehiclePosition);
                    scheduleRepository.updateProgramCompletionStatus(scheduleId,true);
                }

                boolean isBusStop = vehiclePosition.isWhereaboutsStatus();
                if (!isBusStop){
                    stopRegisterRepository.updateExitTimeForRegisterBusStop(scheduleId,vehiclePosition.getCurrentBusStop(), Instant.now());
                    int timeWaited = getTimeExceded(vehiclePosition.getCurrentTimeStoppedForBusStop());
                    boolean haveYouLeftTheBusStop = checkMinWaitTime(vehiclePosition.getMinWaitTimeForBusStop(),timeWaited);
                    if (haveYouLeftTheBusStop){
                        stopRegisterRepository.updateMinTimeShortfall(scheduleId,vehiclePosition.getCurrentBusStop(),timeWaited);
                        stopRegisterRepository.updateMinimumTimeMet(scheduleId, vehiclePosition.getCurrentBusStop(),false);
                        stopRegisterRepository.updateAlertSend(scheduleId,vehiclePosition.getCurrentBusStop(),true);
                        sendAlertForNotExceedingWaitTimeForSegment(userId,deviceId, (long) timeWaited,deviceRequestDTO);
                    }
                    logger.info("no se encuentra en un parametro");
                }
            }
        }

        // Obtener el tipo de geocerca de la programación
        TypeGeofence geofenceType = optimizedSchedule.getTypeGeofence();

        // Manejar el tipo de geocerca
        switch (geofenceType) {
            case CIRCULAR -> handlerGeofenceCircular(deviceRequestDTO, optimizedSchedule,userId);
            case CUADRANGULAR -> handlerGeofenceCuadrangular(deviceRequestDTO, optimizedSchedule);
            case POLIGONAL -> handlerGeofencePoligonal(deviceRequestDTO, optimizedSchedule);
        }

        // Validar ruta explícitamente si está configurado
        if (optimizedSchedule.getValidateRouteExplicit()) {
            boolean offRoute = gpsLibDecoded.isOffRoute();
            if (offRoute){
                String licencePlate = vehicleRepository.getLicencePlateByDeviceId(deviceId);
                String description = "El vehiculo con la matricula "+licencePlate+" se ha salido del la ruta programada";
                Alert alert = createAlert(TIME_NOT_COMPLETED , description,deviceId,userId,deviceLatitude,deviceLongitude);
                ResponseAlert responseAlert = responseAlert(alert);
                alertRepository.create(alert);
                String informationJson = JsonUtils.toJson(responseAlert);
                notificationTraccar.sendNotification(createNotificationDTO(userId, String.valueOf(TIME_NOT_COMPLETED), informationJson));
            }

        }

    }

    private void noTimeComplete(Long userId,Long deviceId,Double latitude,Double longitude) {
        String licencePlate = vehicleRepository.getLicencePlateByDeviceId(deviceId);
        String description = "El vehiculo con la matricula "+licencePlate+" no ha completado el tiempo sobre un paradero";
        Alert alert = createAlert(TIME_NOT_COMPLETED , description,deviceId,userId,latitude,longitude);
        ResponseAlert responseAlert = responseAlert(alert);
        alertRepository.create(alert);
        String informationJson = JsonUtils.toJson(responseAlert);
        notificationTraccar.sendNotification(createNotificationDTO(userId, String.valueOf(TIME_NOT_COMPLETED), informationJson));
        logger.warn("TIEMPO NO COMPLETADO D:");
    }

    private void timeComplete(Long userId, Long deviceId) {
        logger.info("TIEMPO COMPLETADO :D");
    }
    private NotificationDTO createNotificationDTO(Long userId, String title , String description){
        return NotificationDTO.builder()
                .userId(userId)
                .title(title)
                .message(description).build();
    }
    private RouteType getTypeRoute(Optional<RouteType> routeTypeByRouteId) {
        logger.info("Buscando el tipo de ruta");
        return routeTypeByRouteId.orElseThrow(
                () -> new EntityNotFoundException("La entidad no ha sido encontrada")
        );
    }
    private Alert createAlert(AlertType alertType, String description, Long vehicleId, Long userId,
                              Double latitude, Double longitude) {
        return Alert.builder()
                .alertType(alertType) // Tipo de alerta (requerido)
                .description(description) // Descripción de la alerta (requerido)
                .vehicleId(vehicleId) // ID del vehículo asociado
                .userId(userId) // ID del usuario asociado
                .latitude(latitude) // Latitud de la ubicación
                .longitude(longitude) // Longitud de la ubicación
                .createdAt(Instant.now()) // Fecha y hora de creación
                .build();
    }

    private ResponseRouteBusStopSegment getBusStopSegmentByOrder(Long order, List<ResponseRouteBusStopSegment> busStopSegments) {
        return busStopSegments.stream()
                .filter(segment -> segment.getOrder().equals(order))  // Filtra por el campo 'order'
                .findFirst()  // Devuelve el primer resultado que cumpla con la condición
                .orElse(null); // Si no se encuentra, devuelve null
    }

    private boolean isRemainsAtTheSameLocation(Long currentBusStopId, Long lasteIdBusStop) {
        return Objects.equals(currentBusStopId, lasteIdBusStop);
    }
    private ResponseRouteBusStopSegment getBusStopSegmentById(Long busStopId, List<ResponseRouteBusStopSegment> busStopSegments) {
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
    public List<BusStopResponse> getBusStops(List<ResponseRouteBusStopSegment> busStopSegments) {
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

    private List<ResponseRouteBusStopSegment> getSegmentBusStop(Long routeId){
        return routeBusStopSegmentRepository.getSegmentsByRouteId(routeId);
    }

    // Enviar alerta si se excede el tiempo máximo de espera
    private void sendAlertForExceedingWaitTimeForSegment(Long userId, Long deviceId,Long timeStopped,Double latitude, Double longitude) {
        String licencePlate = vehicleRepository.getLicencePlateByDeviceId(deviceId);

        String description = "El vehiculo con matricula "+licencePlate+" ha estado mas tiempo  en el paradero paradero el total fue : " + timeStopped;
        Alert alert = createAlert(TIME_NOT_COMPLETED , description,deviceId,userId,latitude,longitude);
        ResponseAlert responseAlert = responseAlert(alert);

        alertRepository.create(alert);
        String informationJson = JsonUtils.toJson(responseAlert);
        notificationTraccar.sendNotification(createNotificationDTO(userId, String.valueOf(TIME_NOT_COMPLETED), informationJson));
        logger.warn("Alerta: El vehículo ha excedido el tiempo de espera en {}. minutos",timeStopped);
    }
    // Enviar alerta si se excede el tiempo máximo de espera
    private void sendAlertForNotExceedingWaitTimeForSegment(Long userId, Long deviceId,Long timeStopped,DeviceRequestDTO deviceRequestDTO) {
        String licencePlate = vehicleRepository.getLicencePlateByDeviceId(deviceId);

        String description = "El vehiculo con matricula "+licencePlate+" ha estado menos tiempo  en el paradero paradero el total fue : " + timeStopped;
        Alert alert = createAlert(TIME_NOT_COMPLETED , description,deviceId,userId, deviceRequestDTO.getLatitude(), deviceRequestDTO.getLongitude());
        ResponseAlert responseAlert = responseAlert(alert);
        alertRepository.create(alert);
        String informationJson = JsonUtils.toJson(responseAlert);
        notificationTraccar.sendNotification(createNotificationDTO(userId, String.valueOf(TIME_NOT_COMPLETED), informationJson));
        logger.warn("Alerta: El vehículo ha excedido el tiempo de espera en {},{}. Tiempo transcurrido: {} minutos.", deviceRequestDTO.getLatitude(),deviceRequestDTO.getLongitude(), timeStopped);
    }
    private BusStopResponse getCurrentBusStop(Double radiusApplicateForBusStop,Double vehicleLatitude,Double vehicleLongitude,List<BusStopResponse> listBusStop , RouteType type){
        return GeoUtils.getBusStopResponseIdIfWithinProximity(vehicleLatitude,vehicleLongitude,listBusStop, radiusApplicateForBusStop, type);
    }
    // Manejador para geocerca circular
    private void handlerGeofenceCircular(DeviceRequestDTO deviceRequestDTO, OptimizedSchedule schedule, Long userId) {
        ResponseCircularGeofence geofence =  geofenceCircularRepository.findByResourceId(schedule.getGeofenceId());
        boolean isWhitGeofence = GeoUtils.isWithinGeofence(geofence.getLatitude(),geofence.getLongitude(),deviceRequestDTO.getLatitude(),deviceRequestDTO.getLongitude(),geofence.getRadius());
        String licencePlate = vehicleRepository.getLicencePlateByDeviceId(deviceRequestDTO.getDeviceId());

        String description = "El vehiculo con matricula "+licencePlate+" ha salido de la geocerca dela programacion";
        Alert alert = createAlert(TIME_NOT_COMPLETED , description, deviceRequestDTO.getDeviceId(), userId, deviceRequestDTO.getLatitude(), deviceRequestDTO.getLongitude());
        ResponseAlert responseAlert = responseAlert(alert);
        alertRepository.create(alert);
        String informationJson = JsonUtils.toJson(responseAlert);
        notificationTraccar.sendNotification(createNotificationDTO(userId, String.valueOf(TIME_NOT_COMPLETED), informationJson));
        logger.warn("Alerta: El vehículo ha salido de la geocerca");
        if (!isWhitGeofence) {
            notificationTraccar.sendNotification(createNotificationDTO(userId, String.valueOf(GEOFENCE_VIOLATION), informationJson));
        }
    }

    // Manejador para geocerca cuadrangular
    private void handlerGeofenceCuadrangular(DeviceRequestDTO deviceRequestDTO, OptimizedSchedule schedule) {
        // TODO: Implementar manejo de geocerca cuadrangular
    }

    // Manejador para geocerca poligonal
    private void handlerGeofencePoligonal(DeviceRequestDTO deviceRequestDTO, OptimizedSchedule schedule) {
        // TODO: Implementar manejo de geocerca poligonal
    }
    // Método para obtener el primer ID de paradero por el campo 'order'
    public static Long getFirstBusStopId(List<ResponseRouteBusStopSegment> routeBusStops) {
        // Verificar si la lista no está vacía
        if (routeBusStops != null && !routeBusStops.isEmpty()) {
            // Ordenar la lista por el campo 'order' (de menor a mayor)
            Collections.sort(routeBusStops, Comparator.comparingLong(ResponseRouteBusStopSegment::getOrder));

            // Obtener el primer paradero
            ResponseRouteBusStopSegment firstBusStop = routeBusStops.get(0);
            return firstBusStop.getBusStopId(); // Devuelve el ID del primer paradero
        } else {
            logger.warn("La lista de paraderos está vacía.");
            return null;
        }
    }
    // Método para obtener el último ID de paradero por el campo 'order'
    public static Long getLastBusStopId(List<ResponseRouteBusStopSegment> routeBusStops) {
        // Verificar si la lista no está vacía
        if (routeBusStops != null && !routeBusStops.isEmpty()) {
            // Ordenar la lista por el campo 'order' (de menor a mayor)
            Collections.sort(routeBusStops, Comparator.comparingLong(ResponseRouteBusStopSegment::getOrder));

            // Obtener el último paradero
            ResponseRouteBusStopSegment lastBusStop = routeBusStops.get(routeBusStops.size() - 1);
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
    public ResponseAlert responseAlert(Alert alert){
        return ResponseAlert.builder()
                .alertType(alert.getAlertType())
                .latitude(alert.getLatitude())
                .description(alert.getDescription())
                .longitude(alert.getLongitude())
                .createdAt(Instant.now())

                .build();
    }
    public static int getTimeExceded(Instant arrivalTime){
        Instant currentTime = Instant.now();
        return (int) ChronoUnit.MINUTES.between(arrivalTime, currentTime);
    }
    public static boolean checkMinWaitTime(Integer minWaitTime , int timeWaited) {
        return timeWaited < minWaitTime;
    }
}
