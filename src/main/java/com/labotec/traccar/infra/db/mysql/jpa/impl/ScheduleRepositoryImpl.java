package com.labotec.traccar.infra.db.mysql.jpa.impl;

import com.labotec.traccar.app.usecase.ports.input.ScheduleRepository;
import com.labotec.traccar.domain.database.models.Schedule;
import com.labotec.traccar.domain.web.dto.ScheduleDTO;
import com.labotec.traccar.infra.db.mysql.jpa.entity.*;
import com.labotec.traccar.infra.db.mysql.jpa.mapper.ScheduleMapper;
import com.labotec.traccar.infra.db.mysql.jpa.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.labotec.traccar.infra.db.mysql.jpa.message.ComapanyMessage.COMPANY_NOT_FOUND_BY_ID;
import static com.labotec.traccar.infra.db.mysql.jpa.message.LocationMessage.LOCATION_NOT_FOUND_BY_ID;
import static com.labotec.traccar.infra.db.mysql.jpa.message.ScheduleMessage.SCHEDULE_NOT_FOUND_BY_ID;
import static com.labotec.traccar.infra.db.mysql.jpa.message.DriverMessage.DRIVER_NOT_FOUND_BY_ID;
import static com.labotec.traccar.infra.db.mysql.jpa.message.VehicleMessage.VEHICLE_NOT_FOUND_BY_ID;
import static com.labotec.traccar.infra.db.mysql.jpa.message.RouteMessage.ROUTE_NOT_FOUND_BY_ID;

@AllArgsConstructor
@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {

    private final ScheduleRepositoryJpa scheduleRepositoryJpa;
    private final VehicleRepositoryJpa vehicleRepositoryJpa;
    private final DriverRepositoryJpa driverRepositoryJpa;
    private final CompanyRepositoryJpa companyRepositoryJpa;
    private final RouteRepositoryJpa routeRepositoryJpa;
    private final LocationRepositoryJpa locationRepositoryJpa;
    private final ScheduleMapper scheduleMapper;

    @Override
    public Schedule create(ScheduleDTO entity) {
        // Buscar la compañía, vehículo, conductor y ruta asociada
        CompanyEntity company = companyRepositoryJpa.findById(entity.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException(COMPANY_NOT_FOUND_BY_ID + entity.getCompanyId()));
        VehicleEntity vehicle = vehicleRepositoryJpa.findById(entity.getVehicleId())
                .orElseThrow(() -> new EntityNotFoundException(VEHICLE_NOT_FOUND_BY_ID + entity.getVehicleId()));
        DriverEntity driver = driverRepositoryJpa.findById(entity.getDriverId())
                .orElseThrow(() -> new EntityNotFoundException(DRIVER_NOT_FOUND_BY_ID + entity.getDriverId()));
        RouteEntity route = routeRepositoryJpa.findById(entity.getRouteId())
                .orElseThrow(() -> new EntityNotFoundException(ROUTE_NOT_FOUND_BY_ID + entity.getRouteId()));
        LocationEntity location = locationRepositoryJpa.findById(entity.getLocationId())
                .orElseThrow(() -> new EntityNotFoundException(LOCATION_NOT_FOUND_BY_ID + entity.getLocationId()));
        // Crear una entidad ScheduleEntity manualmente a partir del DTO
        ScheduleEntity scheduleEntity = new ScheduleEntity();
        scheduleEntity.setDepartureTime(entity.getDepartureTime());
        scheduleEntity.setArrivalTime(entity.getArrivalTime());
        scheduleEntity.setVehicle(vehicle);
        scheduleEntity.setDriver(driver);
        scheduleEntity.setRoute(route);
        scheduleEntity.setLocation(location);
        scheduleEntity.setSheetNumber(entity.getSheetNumber());
        scheduleEntity.setStatus(entity.getStatus());
        scheduleEntity.setEstimatedDepartureTime(entity.getEstimatedDepartureTime());
        scheduleEntity.setEstimatedArrivalTime(entity.getEstimatedArrivalTime());
        scheduleEntity.setCompany(company);

        // Guardar la entidad en la base de datos
        ScheduleEntity savedSchedule = scheduleRepositoryJpa.save(scheduleEntity);

        // Devolver el modelo de dominio Schedule usando el ScheduleMapper
        return scheduleMapper.toModel(savedSchedule);
    }

    @Override
    public Schedule findById(Integer id) {
        ScheduleEntity scheduleEntity = scheduleRepositoryJpa.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(SCHEDULE_NOT_FOUND_BY_ID + id));
        return scheduleMapper.toModel(scheduleEntity);
    }

    @Override
    public Optional<Schedule> findByIdOptional(Integer id) {
        return scheduleRepositoryJpa.findById(id)
                .map(scheduleMapper::toModel); // Usar el mapper para convertir de entidad a modelo
    }

    @Override
    public List<Schedule> findAll() {
        List<ScheduleEntity> scheduleEntities = scheduleRepositoryJpa.findAll();
        return scheduleMapper.toModelList(scheduleEntities); // Usar el mapper para convertir la lista de entidades
    }

    @Override
    public Schedule update(ScheduleDTO entity, Integer id) {
        // Buscar la entidad ScheduleEntity por su ID
        ScheduleEntity scheduleEntity = scheduleRepositoryJpa.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(SCHEDULE_NOT_FOUND_BY_ID + id));

        // Buscar la compañía, vehículo, conductor y ruta asociada
        CompanyEntity company = companyRepositoryJpa.findById(entity.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException(COMPANY_NOT_FOUND_BY_ID + entity.getCompanyId()));
        VehicleEntity vehicle = vehicleRepositoryJpa.findById(entity.getVehicleId())
                .orElseThrow(() -> new EntityNotFoundException(VEHICLE_NOT_FOUND_BY_ID + entity.getVehicleId()));
        DriverEntity driver = driverRepositoryJpa.findById(entity.getDriverId())
                .orElseThrow(() -> new EntityNotFoundException(DRIVER_NOT_FOUND_BY_ID + entity.getDriverId()));
        RouteEntity route = routeRepositoryJpa.findById(entity.getRouteId())
                .orElseThrow(() -> new EntityNotFoundException(ROUTE_NOT_FOUND_BY_ID + entity.getRouteId()));
        LocationEntity location = locationRepositoryJpa.findById(entity.getLocationId())
                .orElseThrow(() -> new EntityNotFoundException(LOCATION_NOT_FOUND_BY_ID + entity.getLocationId()));
        // Actualizar manualmente los campos con los valores del DTO
        scheduleEntity.setDepartureTime(entity.getDepartureTime());
        scheduleEntity.setArrivalTime(entity.getArrivalTime());
        scheduleEntity.setVehicle(vehicle);
        scheduleEntity.setDriver(driver);
        scheduleEntity.setLocation(location);
        scheduleEntity.setRoute(route);
        scheduleEntity.setSheetNumber(entity.getSheetNumber());
        scheduleEntity.setStatus(entity.getStatus());
        scheduleEntity.setEstimatedDepartureTime(entity.getEstimatedDepartureTime());
        scheduleEntity.setEstimatedArrivalTime(entity.getEstimatedArrivalTime());
        scheduleEntity.setCompany(company);

        // Guardar la entidad actualizada en la base de datos
        ScheduleEntity updatedSchedule = scheduleRepositoryJpa.save(scheduleEntity);

        // Devolver el modelo de dominio actualizado usando el mapper
        return scheduleMapper.toModel(updatedSchedule);
    }

    @Override
    public void deleteById(Integer id) {
        scheduleRepositoryJpa.deleteById(id);
    }
}
