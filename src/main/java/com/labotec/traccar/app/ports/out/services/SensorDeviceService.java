package com.labotec.traccar.app.ports.out.services;

import com.labotec.traccar.domain.database.models.SensorDevice;
import com.labotec.traccar.domain.web.labotec.request.create.CreateSensorDeviceDTO;

public interface SensorDeviceService {
    SensorDevice createSensor(Long userId, CreateSensorDeviceDTO sensorDeviceDTO);

    void deleteByResourceId(Long deviceId, Long resourceId, Long userId);
}
