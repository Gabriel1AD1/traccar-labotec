package com.labotec.traccar.app.ports.input.repository;

import com.labotec.traccar.domain.database.models.SensorValidationConfig;
import com.labotec.traccar.domain.web.dto.labotec.response.ResponseSensorValidatorConfig;

import java.util.List;

public interface SensorValidatorRepository {
    SensorValidationConfig create(SensorValidationConfig model);
    SensorValidationConfig update(SensorValidationConfig model);
    void deleteByDeviceId(Long deviceId);
    void deleteResourceId(Long resourceId, Long userId);
    SensorValidationConfig findById(Long resourceId , Long userId);
    List<ResponseSensorValidatorConfig> findAllByUserIdAndDeviceId(Long userId, Long deviceId);
}
