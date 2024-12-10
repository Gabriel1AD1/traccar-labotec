package com.labotec.traccar.app.implementation;

import com.labotec.traccar.app.exception.AlreadyAssignedVehicleSchedule;
import com.labotec.traccar.app.mapper.model.ScheduleModelMapper;
import com.labotec.traccar.app.ports.input.repository.*;
import com.labotec.traccar.app.ports.out.ScheduleService;
import com.labotec.traccar.domain.database.models.*;
import com.labotec.traccar.domain.database.models.read.InformationRoute;
import com.labotec.traccar.domain.enums.STATE;
import com.labotec.traccar.domain.web.dto.labotec.request.ReportDelayDTO;
import com.labotec.traccar.domain.web.dto.labotec.request.create.DriverRolScheduleCreateDTO;
import com.labotec.traccar.domain.web.dto.labotec.request.create.ScheduleDTO;
import com.labotec.traccar.domain.web.dto.labotec.request.update.UpdateScheduleDTO;
import com.labotec.traccar.domain.web.dto.labotec.response.ResponseRouteBusStopSegment;
import com.labotec.traccar.infra.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor

public class ScheduleServiceI implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final VehicleRepository vehicleRepository;
    private final DriverRepository driverRepository;
    private final LocationRepository locationRepository;
    private final RouteRepository routeRepository;
    private final CompanyRepository companyRepository;
    private final GeofenceCircularRepository overviewPolylineRepository;
    private final ScheduleModelMapper scheduleModelMapper;
    private final DriverScheduleRepository driverScheduleRepository;
    private final UserRepository userRepository;
    private final RouteBusStopResponseSegmentRepository routeBusStopResponseSegmentRepository;
    private final VehiclePositionRepository vehiclePositionRepository;
    private final StopRegisterRepository stopRegisterRepository;
    @Override
    public Schedule create(ScheduleDTO scheduleDTO, Long userId) {
        Instant departureTimeUTC = scheduleDTO.getZoneEstimatedDepartureTime().withZoneSameInstant(ZoneOffset.UTC).toInstant();
        Instant arrivalTimeUTC = scheduleDTO.getZoneEstimatedArrivalTime().withZoneSameInstant(ZoneOffset.UTC).toInstant();

        if (scheduleRepository.existsOverlappingSchedule(scheduleDTO.getVehicleId() ,departureTimeUTC ,arrivalTimeUTC)){
            throw new AlreadyAssignedVehicleSchedule("El vehiculo ya tiene una programación programada dentro del rango "
                    .concat(scheduleDTO.getZoneEstimatedArrivalTime().toString().concat(" y ") . concat(scheduleDTO.getZoneEstimatedDepartureTime().toString()))
            );
        }
        User user = userRepository.findByUserId(userId);
        Vehicle vehicle =  vehicleRepository.findById(scheduleDTO.getVehicleId(),userId);
        Location location = locationRepository.findById(scheduleDTO.getLocationId(),userId);
        Route route = routeRepository.findById(scheduleDTO.getRouteId(),userId);
        Schedule scheduleMap  = scheduleModelMapper.toScheduleDomain(scheduleDTO);
        scheduleMap.setStatus(STATE.ACTIVO);
        scheduleMap.setCompanyId(user.getCompanyId());
        scheduleMap.setVehicle(vehicle);
        scheduleMap.setRoute(route);
        scheduleMap.setUserId(user);
        scheduleMap.setIsProgramCompleted(false);
        scheduleMap.setLocation(location);
        scheduleMap.setEstimatedDepartureTime(departureTimeUTC);
        scheduleMap.setEstimatedArrivalTime(arrivalTimeUTC);
        Schedule scheduleSaved = scheduleRepository.create(scheduleMap);
        VehiclePosition vehiclePosition = getPosition(scheduleSaved.getId(),vehicle.getTraccarDeviceId());
        vehiclePositionRepository.save(vehiclePosition);
        for (DriverRolScheduleCreateDTO driverListAssigment : scheduleDTO.getDriverAssignmentRoute()){
            Driver driver = driverRepository.findById(driverListAssigment.getDriverId(),userId);
            DriverSchedule driverSchedule = new DriverSchedule();
            driverSchedule.setRouteAssignmentRole(driverListAssigment.getRouteAssignmentRole());
            driverSchedule.setUserAllocatorId(user);
            driverSchedule.setScheduleId(scheduleSaved);
            driverSchedule.setDriverId(driver);
            driverScheduleRepository.create(driverSchedule);
        }
        List<ResponseRouteBusStopSegment> listSegment= routeBusStopResponseSegmentRepository.getSegmentsByRouteId(route.getId());
        listSegment.forEach(getBusStopSegments ->
                stopRegisterRepository.create(StopRegister.builder()
                        .alerted(false)
                        .busStopId(getBusStopSegments.getBusStopId())
                        .entryTime(null)
                        .exitTime(null)
                        .isMinimumTimeMet(true)
                        .scheduleId(scheduleSaved.getId())
                        .estimatedTime(getBusStopSegments.getEstimatedTravelTime())
                        .scheduledTime(Instant.now().getNano())
                        .timeExceeded(false)
                        .vehicleId(vehicle.getTraccarDeviceId())
                        .build()
                )
        );

        return scheduleSaved;
    }

    private VehiclePosition getPosition(Long resourceId, Long deviceId) {
        VehiclePosition vehiclePosition = new VehiclePosition();
        vehiclePosition.setScheduleId(resourceId); // Asigna el ID de la programación (resourceId)
        vehiclePosition.setDeviceId(deviceId); // Asigna el ID del dispositivo (deviceId)
        vehiclePosition.setResetRoute(true); // Por defecto, no reiniciar la ruta
        vehiclePosition.setCurrentTimeStoppedForBusStop(null); // No hay tiempo detenido en el paradero aún
        vehiclePosition.setMaxWaitTimeForBusStop(null);
        vehiclePosition.setMinWaitTimeForBusStop(null);
        vehiclePosition.setCurrentTimeStopped(null); // No hay tiempo detenido aún
        vehiclePosition.setWhereaboutsStatus(false);
        vehiclePosition.setLongitude(0.0); // Valor por defecto de la longitud
        vehiclePosition.setLatitude(0.0); // Valor por defecto de la latitud
        vehiclePosition.setNextMinBusStopTimeBusStop(null); // No hay tiempo mínimo asignado aún
        vehiclePosition.setNextMaxBusStopTimeBusStop(null); // No hay tiempo máximo asignado aún
        vehiclePosition.setCurrentSegment(0L); // Por defecto, el segmento actual es 0
        vehiclePosition.setCurrentBusStop(0L); // Por defecto, el paradero actual es 0 (puedes cambiar según tus datos)
        vehiclePosition.setNexBusStopId(0L); // Por defecto, el siguiente paradero es 0 (puedes cambiar según tus datos)
        return vehiclePosition;
    }


    @Override
    public Schedule findById(Long resourceId, Long userId) {
        return scheduleRepository.findById(resourceId,userId);
    }

    @Override
    public Iterable<Schedule> findAll(Long userId) {
        return scheduleRepository.findAll(userId);
    }

    @Override
    public Schedule update(UpdateScheduleDTO updateScheduleDTO, Long resourceId, Long userId) {
        User user = userRepository.findByUserId(userId);
        Vehicle vehicle =  vehicleRepository.findById(updateScheduleDTO.getVehicleId(),userId);
        Location location = locationRepository.findById(updateScheduleDTO.getLocationId(),userId);
        Route route = routeRepository.findById(updateScheduleDTO.getRouteId(),userId);
        Schedule scheduleMap  = scheduleModelMapper.toScheduleDomain(updateScheduleDTO);
        scheduleMap.setCompanyId(user.getCompanyId());
        scheduleMap.setVehicle(vehicle);
        scheduleMap.setLocation(location);
        scheduleMap.setRoute(route);
        scheduleMap.setUserId(user);
        return scheduleRepository.create(scheduleMap);
    }

    @Override
    public void deleteById(Long resourceId, Long userId) {
        scheduleRepository.deleteById(resourceId,userId);
    }

    @Override
    public void updateStatus(Long id, STATE status, Long userId) {
        scheduleRepository.updateStatus(id,status,userId);
    }


    @Override
    public List<Schedule> findAllByDateRange(Instant from, Instant to, Long userId) {
        return scheduleRepository.findAllByDateRange(from, to, userId);
    }

    @Override
    public void updateEstimatedDepartureTime(Long resourceId, Long userId, Instant estimatedDepartureTime) {
        scheduleRepository.updateEstimatedDepartureTime(resourceId, estimatedDepartureTime, userId);
    }

    @Override
    public void updateEstimatedArrivalTime(Long id, Long userId, Instant estimatedDepartureTime) {
        scheduleRepository.updateEstimatedArrivalTime(id,estimatedDepartureTime,userId);
    }

    @Override
    public List<Schedule> findScheduleByVehicle(Long vehicle, Long userId) {
        return scheduleRepository.findByVehicle(vehicle,userId);
    }

    @Override
    public void updateScheduleForDelay(ReportDelayDTO reportDelayDTO) {
        //1 Obtenemos el vehiculo el cual queremos actualizar
        String licencePlate = reportDelayDTO.getPlate();
        Vehicle vehicle = vehicleRepository.findByLicencePlate(licencePlate);
        Instant newArrivalTime = reportDelayDTO.getNewETA().toInstant();
        Long vehicleId = vehicle.getTraccarDeviceId();
        Instant currentTime = Instant.now();
        InformationRoute result = getSchedule(vehicleId,currentTime);
        Long scheduleId = result.getScheduleId();
        Instant estimatedArrivalTime = result.getEstimatedArrivedTime();
        Long routeId = result.getRouteId();
        List<ResponseRouteBusStopSegment> getSegments = getSegmentBusStop(routeId);
        VehiclePosition position = vehiclePositionRepository.findByScheduleId(scheduleId);
        List<ScheduleDelayInformation> findAllSchedule = scheduleRepository.findAllSchedulesForDelay(vehicleId,estimatedArrivalTime);
        Long currentBusStop = position.getCurrentBusStop();
        if (position.getCurrentBusStop() == 0L) {
            // Caso 1: El vehículo no ha llegado al primer paradero
            int totalAdditionalMinutes = getSegmentBusStop(routeId).stream()
                    .mapToInt(ResponseRouteBusStopSegment::getMaxWaitTime)
                    .sum();

            newArrivalTime = newArrivalTime.plus(totalAdditionalMinutes, ChronoUnit.MINUTES);
            long minutesDifference = Duration.between(estimatedArrivalTime, newArrivalTime).toMinutes();

            // Filtrar y actualizar horarios que no han iniciado
            List<ScheduleDelayInformation> listToUpdate = new ArrayList<>();
            for (ScheduleDelayInformation schedule : findAllSchedule) {
                if (!schedule.getEstimatedDepartureTime().isBefore(currentTime)) {
                    listToUpdate.add(
                            ScheduleDelayInformation.builder()
                                    .id(schedule.getId())
                                    .estimatedArrivalTime(schedule.getEstimatedArrivalTime().plus(minutesDifference, ChronoUnit.MINUTES))
                                    .estimatedDepartureTime(schedule.getEstimatedDepartureTime().plus(minutesDifference, ChronoUnit.MINUTES))
                                    .build()
                    );
                }
            }
            listToUpdate.forEach(System.out::println);
            listToUpdate.forEach(s -> scheduleRepository.updateScheduleTimesById(s.getId(),s.getEstimatedDepartureTime(),s.getEstimatedArrivalTime()));
            scheduleRepository.updateEstimatedArrivedTime(scheduleId,newArrivalTime);
            return;
        }

        // Caso 2: El vehículo ha pasado por un paradero
        List<ResponseRouteBusStopSegment> segments = getSegmentBusStop(routeId);
        currentBusStop = position.getCurrentBusStop();
        List<ResponseRouteBusStopSegment> segmentFilter = getSegmentsFollowingBusStop(segments, currentBusStop);

        int totalAdditionalMinutes = segmentFilter.stream()
                .mapToInt(ResponseRouteBusStopSegment::getMaxWaitTime)
                .sum();

        Instant newArrivalTimePlus = newArrivalTime.plus(totalAdditionalMinutes, ChronoUnit.MINUTES);
        long delayMinutes = Duration.between(estimatedArrivalTime, newArrivalTimePlus).toMinutes();

        List<ScheduleDelayInformation> updatedSchedules = new ArrayList<>();

        for (int i = 0; i < findAllSchedule.size(); i++) {
            ScheduleDelayInformation currentSchedule = findAllSchedule.get(i);

            if (i == 0) {
                updatedSchedules.add(
                        ScheduleDelayInformation.builder()
                                .id(currentSchedule.getId())
                                .estimatedArrivalTime(currentSchedule.getEstimatedArrivalTime().plus(delayMinutes, ChronoUnit.MINUTES))
                                .estimatedDepartureTime(currentSchedule.getEstimatedDepartureTime().plus(delayMinutes, ChronoUnit.MINUTES))
                                .build()
                );
            } else {
                ScheduleDelayInformation previousSchedule = updatedSchedules.get(i - 1);

                if (!currentSchedule.getEstimatedArrivalTime().isAfter(previousSchedule.getEstimatedDepartureTime())) {
                    long overlapMinutes = Duration.between(currentSchedule.getEstimatedArrivalTime(), previousSchedule.getEstimatedDepartureTime()).toMinutes();
                    updatedSchedules.add(
                            ScheduleDelayInformation.builder()
                                    .id(currentSchedule.getId())
                                    .estimatedArrivalTime(currentSchedule.getEstimatedArrivalTime().plus(overlapMinutes, ChronoUnit.MINUTES))
                                    .estimatedDepartureTime(currentSchedule.getEstimatedDepartureTime().plus(overlapMinutes, ChronoUnit.MINUTES))
                                    .build()
                    );
                } else {
                    updatedSchedules.add(currentSchedule);
                }
            }
        }

        // Imprimir los horarios ajustados
        System.out.println("Updated Schedules (After First Stop):");
        updatedSchedules.forEach(System.out::println);
        updatedSchedules.forEach(s -> scheduleRepository.updateScheduleTimesById(s.getId(),s.getEstimatedDepartureTime(),s.getEstimatedArrivalTime()));
        scheduleRepository.updateEstimatedArrivedTime(scheduleId,newArrivalTimePlus);

    }
    private List<ResponseRouteBusStopSegment> getSegmentBusStop(Long routeId){
        return routeBusStopResponseSegmentRepository.getSegmentsByRouteId(routeId);
    }
    public List<ResponseRouteBusStopSegment> getSegmentsFollowingBusStop(List<ResponseRouteBusStopSegment> segments, Long busStopId) {
        // Buscar el segmento inicial con el busStopId proporcionado
        Optional<ResponseRouteBusStopSegment> startingSegment = segments.stream()
                .filter(segment -> segment.getBusStopId().equals(busStopId))
                .findFirst();

        // Si se encuentra el segmento, obtenemos este y los siguientes
        if (startingSegment.isPresent()) {
            Long orderOfStart = startingSegment.get().getOrder();

            // Filtrar todos los segmentos cuyo orden sea mayor o igual al del segmento inicial
            return segments.stream()
                    .filter(segment -> segment.getOrder() > orderOfStart)  // > para obtener los siguientes, no el mismo
                    .sorted(Comparator.comparingLong(ResponseRouteBusStopSegment::getOrder)) // Ordenamos por el campo 'order'
                    .collect(Collectors.toList());
        }

        // Si no se encuentra el segmento con ese busStopId, retornar una lista vacía
        return Collections.emptyList();
    }
    private InformationRoute getSchedule(Long vehicleId , Instant currentTime){
        return scheduleRepository.findByInformationSchedule(vehicleId,currentTime).orElseThrow(
                () -> new EntityNotFoundException("No se encontro la ruta")
        );
    }
}
