package com.labotec.traccar.app.implementation;

import com.labotec.traccar.app.ports.input.repository.SensorValidatorRepository;
import com.labotec.traccar.app.ports.input.repository.UserRepository;
import com.labotec.traccar.app.ports.input.repository.VehicleRepository;
import com.labotec.traccar.app.ports.out.services.SensorValidatorService;
import com.labotec.traccar.app.utils.JsonUtils;
import com.labotec.traccar.domain.database.models.SensorValidationConfig;
import com.labotec.traccar.domain.database.models.User;
import com.labotec.traccar.domain.database.models.optimized.TypeValidation;
import com.labotec.traccar.domain.web.dto.labotec.request.create.CreateSensorStaticValidationConfigDTO;
import com.labotec.traccar.domain.web.dto.labotec.request.create.CreateSensorTimeValidationConfigDTO;
import com.labotec.traccar.domain.web.dto.labotec.request.update.UpdateSensorStaticValidationConfigDTO;
import com.labotec.traccar.domain.web.dto.labotec.request.update.UpdateSensorTimeValidationConfigDTO;
import com.labotec.traccar.domain.web.dto.labotec.response.ResponseSensorValidatorConfig;
import com.labotec.traccar.infra.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class SensorValidatorServiceI implements SensorValidatorService {
    private final SensorValidatorRepository sensorValidatorRepository;
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    @Override
    public SensorValidationConfig createValidatorStatic(Long userId, CreateSensorStaticValidationConfigDTO sensorValidationConfig) {
        User user = userRepository.findByUserId(userId);
        Long companyId = user.getCompanyId().getCompanyId();
        Long deviceId = sensorValidationConfig.getDeviceId();
        if (!vehicleRepository.checkPermission(userId,deviceId)){
            throw  new EntityNotFoundException("El vehiculo no ha sido encontrado " + deviceId);
        }
        return sensorValidatorRepository.create(SensorValidationConfig.builder()
                        .state(true)
                        .nameValidation(sensorValidationConfig.getNameValidation())
                        .typeValidation(TypeValidation.STATIC)
                        .userId(userId)
                        .companyId(companyId)
                        .deviceId(sensorValidationConfig.getDeviceId())
                        .nameSensor(sensorValidationConfig.getNameSensor())
                        .value(sensorValidationConfig.getValue())
                        .operator(sensorValidationConfig.getOperator())
                        .dataType(sensorValidationConfig.getDataType())
                        .messageAlert(sensorValidationConfig.getMessageAlert())
                .build());
    }
    @Override
    public SensorValidationConfig updateValidatorStatic(Long userId, Long resourceId , UpdateSensorStaticValidationConfigDTO sensorValidationConfig) {
        SensorValidationConfig sensorDevice = sensorValidatorRepository.findById(resourceId,userId);
        sensorDevice.setState(sensorValidationConfig.getState());
        sensorDevice.setNameValidation(sensorValidationConfig.getNameValidation());
        sensorDevice.setNameSensor(sensorValidationConfig.getNameSensor());
        sensorDevice.setDataType(sensorValidationConfig.getDataType());
        sensorDevice.setOperator(sensorValidationConfig.getOperator());
        sensorDevice.setMessageAlert(sensorValidationConfig.getMessageAlert());
        sensorDevice.setValue(sensorValidationConfig.getValue());
        return sensorValidatorRepository.update(sensorDevice);
    }

    @Override
    public SensorValidationConfig createValidatorTime(Long userId, CreateSensorTimeValidationConfigDTO sensorValidationConfig) {
        User user = userRepository.findByUserId(userId);
        Long companyId = user.getCompanyId().getCompanyId();
        Long deviceId = sensorValidationConfig.getDeviceId();
        if (!vehicleRepository.checkPermission(userId,deviceId)){
            throw  new EntityNotFoundException("El vehiculo no ha sido encontrado " + deviceId);
        }
        String value = JsonUtils.toJson(sensorValidationConfig.getValidatorTime());
        return sensorValidatorRepository.create(SensorValidationConfig.builder()
                .state(true)
                .typeValidation(TypeValidation.TIME)
                .nameValidation(sensorValidationConfig.getNameValidation())
                .userId(userId)
                .companyId(companyId)
                .deviceId(sensorValidationConfig.getDeviceId())
                .nameSensor(sensorValidationConfig.getNameSensor())
                .value(value)
                .operator(sensorValidationConfig.getOperator())
                .dataType(sensorValidationConfig.getDataType())
                .messageAlert(sensorValidationConfig.getMessageAlert())
                .build());
    }

    @Override
    public SensorValidationConfig updateValidatorTime(Long userId, Long resourceId, UpdateSensorTimeValidationConfigDTO sensorValidationConfig) {
        String value = JsonUtils.toJson(sensorValidationConfig.getValidatorTime());
        SensorValidationConfig sensorDevice = sensorValidatorRepository.findById(resourceId,userId);
        sensorDevice.setNameValidation(sensorValidationConfig.getNameValidation());
        sensorDevice.setState(sensorValidationConfig.getState());
        sensorDevice.setNameSensor(sensorValidationConfig.getNameSensor());
        sensorDevice.setDataType(sensorValidationConfig.getDataType());
        sensorDevice.setOperator(sensorValidationConfig.getOperator());
        sensorDevice.setMessageAlert(sensorValidationConfig.getMessageAlert());
        sensorDevice.setValue(value);
        return sensorValidatorRepository.update(sensorDevice);

    }


    @Override
    public void deleteByUserIdAndResourceId(Long userId, Long resourceId){
        sensorValidatorRepository.deleteResourceId(resourceId,userId);
    }
    @Override
    public List<ResponseSensorValidatorConfig> findAllByDeviceId(Long userId, Long deviceId){
        return sensorValidatorRepository.findAllByUserIdAndDeviceId(userId,deviceId);
    }
}
