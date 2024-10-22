package com.labotec.traccar.app.implemenatition;

import com.labotec.traccar.app.mapper.ScheduleModelMapper;
import com.labotec.traccar.app.usecase.ports.input.*;
import com.labotec.traccar.app.usecase.ports.out.ScheduleService;
import com.labotec.traccar.domain.database.models.*;
import com.labotec.traccar.domain.web.dto.ScheduleDTO;
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
    private final ScheduleModelMapper scheduleModelMapper;

    @Override
    public Schedule create(ScheduleDTO entityDto) {
        Vehicle vehicle =  vehicleRepository.findById(entityDto.getVehicleId());
        Driver driver = driverRepository.findById(entityDto.getDriverId());
        Location location = locationRepository.findById(entityDto.getLocationId());
        Route route = routeRepository.findById(entityDto.getRouteId());
        Company company = companyRepository.findById(entityDto.getCompanyId());
        Schedule scheduleMap  = scheduleModelMapper.INSTANCE.toScheduleDomain(entityDto);
        scheduleMap.setVehicle(vehicle);
        scheduleMap.setDriver(driver);
        scheduleMap.setLocation(location);
        scheduleMap.setRoute(route);
        scheduleMap.setCompany(company);
        return scheduleRepository.create(scheduleMap);
    }

    @Override
    public Schedule findById(Integer integer) {
        return scheduleRepository.findById(integer);
    }

    @Override
    public Iterable<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    @Override
    public Schedule update(ScheduleDTO entityDto, Integer integer) {
        Vehicle vehicleFind = vehicleRepository.findById(integer);
        Vehicle vehicle =  vehicleRepository.findById(entityDto.getVehicleId());
        Driver driver = driverRepository.findById(entityDto.getDriverId());
        Location location = locationRepository.findById(entityDto.getLocationId());
        Route route = routeRepository.findById(entityDto.getRouteId());
        Company company = companyRepository.findById(entityDto.getCompanyId());
        Schedule scheduleMap  = scheduleModelMapper.INSTANCE.toScheduleDomain(entityDto);
        scheduleMap.setId(vehicleFind.getId());
        scheduleMap.setVehicle(vehicle);
        scheduleMap.setDriver(driver);
        scheduleMap.setLocation(location);
        scheduleMap.setRoute(route);
        scheduleMap.setCompany(company);
        return scheduleRepository.update(scheduleMap);
    }

    @Override
    public void deleteById(Integer integer) {
        scheduleRepository.deleteById(integer);
    }

    @Override
    public Schedule updateStatus(Integer id, Byte status) {
        return scheduleRepository.updateStatus(id,status);
    }

    @Override
    public List<Schedule> findAllByDateRange(Instant from, Instant to) {
        return scheduleRepository.findAllByDateRange(from , to);
    }

    @Override
    public Schedule updateEstimatedDepartureTime(Integer id, Instant estimatedDepartureTime) {
        return scheduleRepository.updateEstimatedDepartureTime(id,estimatedDepartureTime);
    }

    @Override
    public Schedule updateEstimatedArrivalTime(Integer id, Instant estimatedArrivalTime) {
        return scheduleRepository.updateEstimatedArrivalTime(id , estimatedArrivalTime);
    }

    @Override
    public List<Schedule> findTripsByVehicleId(Integer vehicleId) {
        return scheduleRepository.findTripsByVehicleId(vehicleId);
    }
}
