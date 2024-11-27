package com.labotec.traccar.infra.db.mysql.jpa.labotec.impl;

import com.labotec.traccar.app.ports.input.repository.StateVehiclePositionRepository;
import com.labotec.traccar.domain.database.models.StateVehiclePosition;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.StateVehiclePositionEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.StateVehiclePositionMapper;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.StateVehiclePositionEntityRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class StateVehiclePositionRepositoryImpl implements StateVehiclePositionRepository {
    private final StateVehiclePositionEntityRepositoryJpa repositoryJpa;
    private final StateVehiclePositionMapper vehiclePositionMapper;
    @Override
    public StateVehiclePosition create(StateVehiclePosition stateVehiclePosition) {
        StateVehiclePositionEntity positionEntity = vehiclePositionMapper.toEntity(stateVehiclePosition);
        return vehiclePositionMapper.toModel(repositoryJpa.save(positionEntity));
    }

    @Override
    public Optional<StateVehiclePosition> findByTraccarDeviceId(Long deviceId) {
        StateVehiclePositionEntity positionEntity = repositoryJpa.findById(deviceId).orElse(null);
        StateVehiclePosition stateVehiclePosition = vehiclePositionMapper.toModel(positionEntity);
        return Optional.ofNullable(stateVehiclePosition);
    }
}
