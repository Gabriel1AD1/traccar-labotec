package com.labotec.traccar.infra.db.mysql.jpa.labotec.impl;

import com.labotec.traccar.app.ports.input.repository.VehicleTypeRepository;
import com.labotec.traccar.domain.database.models.VehicleType;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.VehicleTypeEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.VehicleTypeMapper;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.VehicleTypeRepositoryJpa;
import com.labotec.traccar.infra.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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
    public VehicleType findById(Long resourceId, Long userId) {
        VehicleTypeEntity vehicleTypeEntity = vehicleTypeRepositoryJpa.findByIdAndUserId(resourceId,userId).orElseThrow(
                () -> new EntityNotFoundException("Tipo de vehiculo no encontrado")
        );
        return vehicleTypeMapper.toDomain(vehicleTypeEntity);
    }

    @Override
    public Optional<VehicleType> findByIdOptional(Long resourceId, Long userId) {
        return Optional.empty();
    }

    @Override
    public Iterable<VehicleType> findAll(Long userId) {
        return null;
    }

    @Override
    public VehicleType update(VehicleType entity) {
        return null;
    }

    @Override
    public void deleteById(Long resourceId, Long userId) {

    }

}
