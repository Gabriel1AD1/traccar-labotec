package com.labotec.traccar.infra.db.mysql.jpa.labotec.impl;

import com.labotec.traccar.app.ports.input.repository.DriverRepository;
import com.labotec.traccar.app.ports.input.repository.DriverScheduleRepository;
import com.labotec.traccar.domain.database.models.DriverSchedule;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.DriverScheduleEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.DriverScheduleMapper;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.DriverScheduleRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
@AllArgsConstructor
public class DriverScheduleRepositoryImpl implements DriverScheduleRepository {
    private final DriverScheduleRepositoryJpa driverScheduleRepositoryJpa;
    private final DriverScheduleMapper driverScheduleMapper;

    @Override
    public DriverSchedule create(DriverSchedule entity) {
        DriverScheduleEntity driverScheduleEntity = driverScheduleMapper.toEntity(entity);
        return driverScheduleMapper.toModel(driverScheduleRepositoryJpa.save(driverScheduleEntity));
    }

    @Override
    public DriverSchedule findById(Long resourceId, Long userId) {
        return null;
    }

    @Override
    public Optional<DriverSchedule> findByIdOptional(Long resourceId, Long userId) {
        return Optional.empty();
    }

    @Override
    public Iterable<DriverSchedule> findAll(Long userId) {
        return null;
    }

    @Override
    public DriverSchedule update(DriverSchedule entity) {
        return null;
    }

    @Override
    public void deleteById(Long resourceId, Long userId) {

    }
}
