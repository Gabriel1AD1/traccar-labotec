package com.labotec.traccar.infra.db.mysql.jpa.labotec.impl;

import com.labotec.traccar.app.usecase.ports.input.repository.VehicleRepository;
import com.labotec.traccar.domain.database.models.Vehicle;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.VehicleEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.VehicleMapper;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.CompanyRepositoryJpa;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.VehicleRepositoryJpa;
import com.labotec.traccar.infra.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.labotec.traccar.infra.db.mysql.jpa.labotec.message.VehicleMessage.VEHICLE_NOT_FOUND_BY_ID;

@AllArgsConstructor
@Repository
public class VehicleRepositoryImpl implements VehicleRepository {

    private final VehicleRepositoryJpa vehicleRepositoryJpa;
    private final CompanyRepositoryJpa companyRepositoryJpa;
    private final VehicleMapper vehicleMapper;

    @Override
    public Vehicle create(Vehicle entity) {
        VehicleEntity vehicleEntity = vehicleMapper.toEntity(entity);
        VehicleEntity vehicleSaved = vehicleRepositoryJpa.save(vehicleEntity);
        return vehicleMapper.toModel(vehicleSaved);
    }

    @Override
    public Vehicle findById(Long resourceId, Long userId) {
        return null;
    }

    @Override
    public Optional<Vehicle> findByIdOptional(Long resourceId, Long userId) {
        return Optional.empty();
    }

    @Override
    public Iterable<Vehicle> findAll(Long userId) {
        return null;
    }

    @Override
    public Vehicle update(Vehicle entity) {
        VehicleEntity vehicleEntity = vehicleMapper.toEntity(entity);
        VehicleEntity vehicleSaved = vehicleRepositoryJpa.save(vehicleEntity);
        return vehicleMapper.toModel(vehicleSaved);
    }

    @Override
    public void deleteById(Long resourceId, Long userId) {

    }



    @Override
    public Vehicle findByDevice(Long id) {
        return null;
    }
}
