package com.labotec.traccar.infra.db.mysql.jpa.labotec.impl;

import com.labotec.traccar.app.ports.input.repository.DriverRepository;
import com.labotec.traccar.domain.database.models.Driver;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.DriverEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.DriverMapper;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.DriverRepositoryJpa;
import com.labotec.traccar.infra.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
    public Driver findById(Long resourceId, Long userId) {
        DriverEntity findByResourceIdAndUserId = driverRepositoryJpa.findByIdAndUserId(resourceId,userId).orElseThrow(
                () -> new EntityNotFoundException("Conductor no encontrado")
        );
        return driverMapper.toModel(findByResourceIdAndUserId);
    }

    @Override
    public Optional<Driver> findByIdOptional(Long resourceId, Long userId) {
        DriverEntity findByResourceIdAndUserId = driverRepositoryJpa.findByIdAndUserId(resourceId,userId).get();
        Driver driver = driverMapper.toModel(findByResourceIdAndUserId);
        return Optional.ofNullable(driver);
    }

    @Override
    public Iterable<Driver> findAll(Long userId) {
        List<DriverEntity> driverEntityList = driverRepositoryJpa.findAllByUserId(userId);
        return driverMapper.toModelList(driverEntityList);
    }


    @Override
    public Driver update(Driver entity) {
        DriverEntity driverEntity = driverMapper.toEntity(entity);
        DriverEntity savedDriver = driverRepositoryJpa.save(driverEntity);

        return driverMapper.toModel(savedDriver);
    }

    @Override
    public void deleteById(Long resourceId, Long userId) {
        driverRepositoryJpa.deleteByIdAndUserId(resourceId,userId);
    }

}
