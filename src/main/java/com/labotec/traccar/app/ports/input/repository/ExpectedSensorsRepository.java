package com.labotec.traccar.app.ports.input.repository;

import com.labotec.traccar.app.utils.common.repository.GenericRepository;
import com.labotec.traccar.domain.database.models.ExpectedSensors;
import com.labotec.traccar.domain.web.labotec.response.ResponseExpectedSensor;

import java.util.List;

public interface ExpectedSensorsRepository extends GenericRepository<ExpectedSensors,Long> {

    List<ResponseExpectedSensor> findAllByDeviceId(Long deviceId, Long userId);

    ExpectedSensors findByDeviceIdAndSensorNameAndUserId(Long deviceId,String name, Long userId);

    ExpectedSensors findByDeviceIdAndSensorId(Long deviceId,Long sensorId);
}
