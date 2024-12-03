package com.labotec.traccar.app.ports.input.repository;

import com.labotec.traccar.domain.database.models.VehiclePosition;

public interface VehiclePositionRepository {

    VehiclePosition findById(long id);
    VehiclePosition save(VehiclePosition vehiclePosition);
    VehiclePosition findByScheduleId(Long scheduleId);
}
