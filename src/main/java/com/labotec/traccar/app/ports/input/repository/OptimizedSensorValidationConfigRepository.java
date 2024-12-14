package com.labotec.traccar.app.ports.input.repository;

import com.labotec.traccar.domain.database.models.optimized.OptimizedSensorValidationConfig;

import java.util.List;

public interface OptimizedSensorValidationConfigRepository {

    List<OptimizedSensorValidationConfig> findAllByDeviceId(Long deviceId);
}
