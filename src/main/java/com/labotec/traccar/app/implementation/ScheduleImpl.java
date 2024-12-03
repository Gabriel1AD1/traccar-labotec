package com.labotec.traccar.app.implementation;

import com.labotec.traccar.app.exception.AlreadyAssignedVehicleSchedule;
import com.labotec.traccar.app.mapper.model.ScheduleModelMapper;
import com.labotec.traccar.app.ports.input.repository.*;
import com.labotec.traccar.app.ports.out.ScheduleService;
import com.labotec.traccar.domain.database.models.*;
import com.labotec.traccar.domain.enums.STATE;
import com.labotec.traccar.domain.web.dto.labotec.request.create.DriverRolScheduleCreateDTO;
import com.labotec.traccar.domain.web.dto.labotec.request.create.ScheduleDTO;
import com.labotec.traccar.domain.web.dto.labotec.request.update.ScheduleUpdateDTO;
import com.labotec.traccar.domain.web.dto.labotec.response.RouteBusStopSegmentResponse;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

@AllArgsConstructor

public class ScheduleImpl implements ScheduleService {
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
        List<RouteBusStopSegmentResponse> listSegment= routeBusStopResponseSegmentRepository.getSegmentsByRouteId(route.getId());
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
    public Schedule update(ScheduleUpdateDTO scheduleUpdateDTO, Long resourceId, Long userId) {
        User user = userRepository.findByUserId(userId);
        Vehicle vehicle =  vehicleRepository.findById(scheduleUpdateDTO.getVehicleId(),userId);
        Location location = locationRepository.findById(scheduleUpdateDTO.getLocationId(),userId);
        Route route = routeRepository.findById(scheduleUpdateDTO.getRouteId(),userId);
        Schedule scheduleMap  = scheduleModelMapper.toScheduleDomain(scheduleUpdateDTO);
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
}
