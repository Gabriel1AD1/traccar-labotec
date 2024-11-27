package com.labotec.traccar.app.ports.input.repository;

import com.labotec.traccar.domain.database.models.StateVehiclePosition;

import java.util.Optional;

public interface StateVehiclePositionRepository {
    StateVehiclePosition create(StateVehiclePosition stateVehiclePosition);
    Optional<StateVehiclePosition> findByTraccarDeviceId(Long deviceId);
}
