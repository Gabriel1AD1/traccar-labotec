package com.labotec.traccar.infra.db.mysql.jpa.labotec.impl;

import com.labotec.traccar.app.usecase.ports.input.repository.VehicleTypeRepository;
import com.labotec.traccar.domain.database.models.VehicleType;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.VehicleTypeEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.VehicleTypeMapper;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.VehicleTypeRepositoryJpa;
import com.labotec.traccar.infra.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.labotec.traccar.infra.db.mysql.jpa.labotec.message.VehicleTypeMessage.VEHICLE_TYPE_NOT_FOUND;

@Repository
@AllArgsConstructor
public class VehicleTypeRepositoryImpl implements VehicleTypeRepository {

    private final VehicleTypeMapper vehicleTypeMapper;
    private final VehicleTypeRepositoryJpa vehicleTypeRepositoryJpa;
    @Override
    public VehicleType create(VehicleType entity) {
        VehicleTypeEntity vehicleEntity = vehicleTypeMapper.toEntity(entity);
        VehicleTypeEntity vehicleSaved = vehicleTypeRepositoryJpa.save(vehicleEntity);
        return vehicleTypeMapper.toDomain(vehicleSaved);
    }

    @Override
    public VehicleType findById(Integer integer) {
        VehicleTypeEntity vehicleTypeEntity = vehicleTypeRepositoryJpa.findById(integer).orElseThrow(
                () -> new EntityNotFoundException(VEHICLE_TYPE_NOT_FOUND)
        );
        return vehicleTypeMapper.toDomain(vehicleTypeEntity);
    }

    @Override
    public Optional<VehicleType> findByIdOptional(Integer integer) {
        return vehicleTypeRepositoryJpa.findById(integer)
                .map(vehicleTypeMapper::toDomain);
    }

    @Override
    public Iterable<VehicleType> findAll() {
        return null;
    }

    @Override
    public VehicleType update(VehicleType entity) {
        return null;
    }

    @Override
    public void deleteById(Integer integer) {

    }
}
