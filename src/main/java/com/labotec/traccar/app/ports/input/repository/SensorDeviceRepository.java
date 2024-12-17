package com.labotec.traccar.app.ports.input.repository;

import com.labotec.traccar.domain.database.models.SensorDevice;
import com.labotec.traccar.domain.web.labotec.response.ResponseSensorDevice;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface SensorDeviceRepository  {
    SensorDevice updateAndReturn(Long deviceId, String nameSensor, String stateCurrent);
    SensorDevice create(SensorDevice sensorDevice);
    SensorDevice update(SensorDevice sensorDevice);
    void deleteByDeviceId(Long deviceId, Long resourceId);
    List<ResponseSensorDevice> findAllByDevice(Long deviceId);

    boolean existSensorByDeviceIdAndSensorId(Long deviceId, @NotNull(message = "El nombre del sensor es obligatorio.") String sensorName);
}
