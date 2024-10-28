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
    public Vehicle findById(Integer id) {
        VehicleEntity vehicleEntity = vehicleRepositoryJpa.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(VEHICLE_NOT_FOUND_BY_ID + id));
        return vehicleMapper.toModel(vehicleEntity);
    }

    @Override
    public Optional<Vehicle> findByIdOptional(Integer id) {
        return vehicleRepositoryJpa.findById(id)
                .map(vehicleMapper::toModel); // Usar el mapper para convertir de entidad a modelo
    }

    @Override
    public List<Vehicle> findAll() {
        List<VehicleEntity> vehicleEntities = vehicleRepositoryJpa.findAll();
        return vehicleMapper.toModelList(vehicleEntities); // Usar el mapper para convertir la lista de entidades
    }

    @Override
    public Vehicle update(Vehicle entity) {
        VehicleEntity vehicleEntity = vehicleMapper.toEntity(entity);
        VehicleEntity vehicleSaved = vehicleRepositoryJpa.save(vehicleEntity);
        return vehicleMapper.toModel(vehicleSaved);
    }


    @Override
    public void deleteById(Integer id) {
        vehicleRepositoryJpa.deleteById(id);
    }

    @Override
    public Vehicle findByDevice(Integer id) {
        VehicleEntity vehicleEntity = vehicleRepositoryJpa.findByTraccarDeviceId(id);
        return vehicleMapper.toModel(vehicleEntity);
    }
}
