package com.labotec.traccar.infra.db.mysql.jpa.labotec.impl;

import com.labotec.traccar.app.usecase.ports.input.repository.DriverRepository;
import com.labotec.traccar.domain.database.models.Driver;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.DriverEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.DriverMapper;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.DriverRepositoryJpa;
import com.labotec.traccar.infra.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.labotec.traccar.infra.db.mysql.jpa.labotec.message.DriverMessage.DRIVER_NOT_FOUND_BY_ID;

@AllArgsConstructor
@Repository
public class DriverRepositoryImpl implements DriverRepository {

    private final DriverRepositoryJpa driverRepositoryJpa;
    private final DriverMapper driverMapper;

    @Override
    public Driver create(Driver entity) {
        DriverEntity driverEntity = driverMapper.toEntity(entity);
        DriverEntity savedDriver = driverRepositoryJpa.save(driverEntity);

        return driverMapper.toModel(savedDriver);
    }

    @Override
    public Driver findById(Integer id) {
        DriverEntity driverEntity = driverRepositoryJpa.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(DRIVER_NOT_FOUND_BY_ID + id));
        return driverMapper.toModel(driverEntity);
    }

    @Override
    public Optional<Driver> findByIdOptional(Integer id) {
        return driverRepositoryJpa.findById(id)
                .map(driverMapper::toModel); // Usar el mapper para convertir de entidad a modelo
    }

    @Override
    public List<Driver> findAll() {
        List<DriverEntity> driverEntities = driverRepositoryJpa.findAll();
        return driverMapper.toModelList(driverEntities); // Usar el mapper para convertir la lista de entidades
    }

    @Override
    public Driver update(Driver entity) {
        DriverEntity driverEntity = driverMapper.toEntity(entity);
        DriverEntity savedDriver = driverRepositoryJpa.save(driverEntity);

        return driverMapper.toModel(savedDriver);
    }

    @Override
    public void deleteById(Integer id) {
        driverRepositoryJpa.deleteById(id);
    }
}
