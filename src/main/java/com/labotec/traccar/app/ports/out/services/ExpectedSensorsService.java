package com.labotec.traccar.app.ports.out.services;

import com.labotec.traccar.domain.database.models.ExpectedSensors;
import com.labotec.traccar.domain.web.labotec.request.create.CreateExpectedSensorsDTO;
import com.labotec.traccar.domain.web.labotec.response.ResponseExpectedSensor;

import java.util.List;

public interface ExpectedSensorsService {
    ExpectedSensors create(Long userId, CreateExpectedSensorsDTO dto);
    List<ResponseExpectedSensor> findAll(Long userId,Long deviceId);
    void delete(Long userId , Long resourceId);
}
