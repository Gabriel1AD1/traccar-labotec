package com.labotec.traccar.app.ports.out.services;

import com.labotec.traccar.domain.database.models.SensorValidationConfig;
import com.labotec.traccar.domain.web.labotec.request.create.CreateSensorExistValidationConfigDTO;
import com.labotec.traccar.domain.web.labotec.request.create.CreateSensorRangeValidationConfigDTO;
import com.labotec.traccar.domain.web.labotec.request.create.CreateSensorStaticValidationConfigDTO;
import com.labotec.traccar.domain.web.labotec.request.create.CreateSensorTimeValidationConfigDTO;
import com.labotec.traccar.domain.web.labotec.request.update.UpdateSensorExistValidationConfigDTO;
import com.labotec.traccar.domain.web.labotec.request.update.UpdateSensorRangeValidationConfigDTO;
import com.labotec.traccar.domain.web.labotec.request.update.UpdateSensorStaticValidationConfigDTO;
import com.labotec.traccar.domain.web.labotec.request.update.UpdateSensorTimeValidationConfigDTO;
import com.labotec.traccar.domain.web.labotec.response.ResponseSensorValidatorConfig;

import java.util.List;

public interface SensorValidatorService {
    SensorValidationConfig createValidatorStatic(Long userId , CreateSensorStaticValidationConfigDTO sensorValidationConfig);
    SensorValidationConfig updateValidatorStatic(Long userId , Long resourceId , UpdateSensorStaticValidationConfigDTO sensorValidationConfig);
    SensorValidationConfig createValidatorTime(Long userId , CreateSensorTimeValidationConfigDTO sensorValidationConfig);
    SensorValidationConfig updateValidatorTime(Long userId , Long resourceId , UpdateSensorTimeValidationConfigDTO sensorValidationConfig);
    SensorValidationConfig createValidatorRange(Long userId , CreateSensorRangeValidationConfigDTO dto);
    SensorValidationConfig updateValidatorRange(Long userId , Long resourceId , UpdateSensorRangeValidationConfigDTO dto);
    SensorValidationConfig createValidatorExist(Long userId , CreateSensorExistValidationConfigDTO dto);
    SensorValidationConfig updateValidatorTime(Long userId , Long resourceId , UpdateSensorExistValidationConfigDTO sensorValidationConfig);

    void deleteByUserIdAndResourceId(Long userId, Long resourceId);

    List<ResponseSensorValidatorConfig> findAllByDeviceId(Long userId, Long deviceId);
}
