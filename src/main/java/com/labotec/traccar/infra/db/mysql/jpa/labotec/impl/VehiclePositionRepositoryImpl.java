package com.labotec.traccar.infra.db.mysql.jpa.labotec.impl;

import com.labotec.traccar.app.ports.input.repository.VehiclePositionRepository;
import com.labotec.traccar.domain.database.models.VehiclePosition;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.VehiclePositionEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.VehiclePositionMapper;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.VehiclePositionRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class VehiclePositionRepositoryImpl
implements VehiclePositionRepository {
    private final VehiclePositionMapper vehiclePositionMapper;
    private final VehiclePositionRepositoryJpa vehiclePositionRepositoryJpa;
    @Override
    public VehiclePosition findById(long id) {
        return vehiclePositionMapper.toDomain(vehiclePositionRepositoryJpa.findById(id).orElse(null));
    }

    @Override
    public VehiclePosition save(VehiclePosition vehiclePosition) {
        VehiclePositionEntity positionEntity = vehiclePositionMapper.toEntity(vehiclePosition);
        return vehiclePositionMapper.toDomain(vehiclePositionRepositoryJpa.save(positionEntity));
    }

    public VehiclePosition findByScheduleId(Long scheduleId) {

        return vehiclePositionMapper.toDomain(vehiclePositionRepositoryJpa.findByScheduleId(scheduleId));
    }
}
