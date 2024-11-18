package com.labotec.traccar.app.implementation;

import com.labotec.traccar.app.mapper.ScheduleModelMapper;
import com.labotec.traccar.app.usecase.ports.input.repository.*;
import com.labotec.traccar.app.usecase.ports.out.ScheduleService;
import com.labotec.traccar.domain.database.models.*;
import com.labotec.traccar.domain.enums.STATE;
import com.labotec.traccar.domain.web.dto.entel.create.ScheduleDTO;
import com.labotec.traccar.domain.web.dto.entel.update.ScheduleUpdateDTO;
import lombok.AllArgsConstructor;

import java.time.Instant;
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
    private final UserRepository userRepository;



    @Override
    public Schedule create(ScheduleDTO scheduleDTO, Long userId) {
        User user = userRepository.findByUserId(userId);
        Vehicle vehicle =  vehicleRepository.findById(scheduleDTO.getVehicleId(),userId);
        Driver driver = driverRepository.findById(scheduleDTO.getDriverId(),userId);
        Location location = locationRepository.findById(scheduleDTO.getLocationId(),userId);
        Route route = routeRepository.findById(scheduleDTO.getRouteId(),userId);
        Schedule scheduleMap  = scheduleModelMapper.toScheduleDomain(scheduleDTO);
        CircularGeofence circularGeofence = overviewPolylineRepository.findById(scheduleDTO.getGeofencePoligonalId(),userId);
        scheduleMap.setCompanyId(user.getCompanyId());
        scheduleMap.setGeofence(circularGeofence);
        scheduleMap.setVehicle(vehicle);
        scheduleMap.setDriver(driver);
        scheduleMap.setLocation(location);
        scheduleMap.setRoute(route);
        scheduleMap.setUserId(user);
        return scheduleRepository.create(scheduleMap);
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
        Driver driver = driverRepository.findById(scheduleUpdateDTO.getDriverId(),userId);
        Location location = locationRepository.findById(scheduleUpdateDTO.getLocationId(),userId);
        Route route = routeRepository.findById(scheduleUpdateDTO.getRouteId(),userId);
        Schedule scheduleMap  = scheduleModelMapper.toScheduleDomain(scheduleUpdateDTO);
        CircularGeofence circularGeofence = overviewPolylineRepository.findById(scheduleUpdateDTO.getGeofencePoligonalId(),userId);
        scheduleMap.setCompanyId(user.getCompanyId());
        scheduleMap.setGeofence(circularGeofence);
        scheduleMap.setVehicle(vehicle);
        scheduleMap.setDriver(driver);
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