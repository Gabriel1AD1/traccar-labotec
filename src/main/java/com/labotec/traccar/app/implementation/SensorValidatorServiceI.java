package com.labotec.traccar.app.implementation;

import com.labotec.traccar.app.exception.BadRequestValidateValueException;
import com.labotec.traccar.app.ports.input.repository.*;
import com.labotec.traccar.app.ports.out.services.SensorValidatorService;
import com.labotec.traccar.app.utils.JsonUtils;
import com.labotec.traccar.domain.database.models.ExpectedSensors;
import com.labotec.traccar.domain.database.models.SensorDevice;
import com.labotec.traccar.domain.database.models.SensorValidationConfig;
import com.labotec.traccar.domain.database.models.User;
import com.labotec.traccar.domain.database.models.optimized.TypeValidation;
import com.labotec.traccar.domain.enums.DataType;
import com.labotec.traccar.domain.web.labotec.request.create.CreateSensorExistValidationConfigDTO;
import com.labotec.traccar.domain.web.labotec.request.create.CreateSensorRangeValidationConfigDTO;
import com.labotec.traccar.domain.web.labotec.request.create.CreateSensorStaticValidationConfigDTO;
import com.labotec.traccar.domain.web.labotec.request.create.CreateSensorTimeValidationConfigDTO;
import com.labotec.traccar.domain.web.labotec.request.update.UpdateSensorExistValidationConfigDTO;
import com.labotec.traccar.domain.web.labotec.request.update.UpdateSensorRangeValidationConfigDTO;
import com.labotec.traccar.domain.web.labotec.request.update.UpdateSensorStaticValidationConfigDTO;
import com.labotec.traccar.domain.web.labotec.request.update.UpdateSensorTimeValidationConfigDTO;
import com.labotec.traccar.domain.web.labotec.response.ResponseSensorValidatorConfig;
import com.labotec.traccar.app.exception.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@Service
public class SensorValidatorServiceI implements SensorValidatorService {
    private final SensorValidatorRepository sensorValidatorRepository;
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    private final ExpectedSensorsRepository expectedSensorsRepository;
    private final SensorDeviceRepository sensorDeviceRepository;
    @Override
    public SensorValidationConfig createValidatorStatic(Long userId, CreateSensorStaticValidationConfigDTO sensorValidationConfig) {
        User user = userRepository.findByUserId(userId);
        Long companyId = user.getCompanyId().getCompanyId();
        Long deviceId = sensorValidationConfig.getDeviceId();
        Long sensorId = sensorValidationConfig.getSensorId();
        ExpectedSensors sensors = expectedSensorsRepository.findByDeviceIdAndSensorId(deviceId,sensorId);
        if (!vehicleRepository.checkPermission(userId,deviceId)){handlerNotAccess(deviceId);}
        if (!validateDataType(sensors.getDataType(), sensorValidationConfig.getValue())){handlerValidateError(sensors.getDataType(),sensorValidationConfig.getValue());}
        return sensorValidatorRepository.create(SensorValidationConfig.builder()
                        .typeSensor(sensors.getTypeSensor())
                        .state(true)
                        .nameValidation(sensorValidationConfig.getNameValidation())
                        .typeValidation(TypeValidation.STATIC)
                        .userId(userId)
                        .companyId(companyId)
                        .deviceId(sensorValidationConfig.getDeviceId())
                        .nameSensor(sensors.getNameSensor())
                        .value(sensorValidationConfig.getValue())
                        .operator(sensorValidationConfig.getOperator())
                        .dataType(sensors.getDataType())
                        .messageAlert(sensorValidationConfig.getMessageAlert())
                .build());
    }

    private void handlerNotAccess(Long deviceId) {
        throw new EntityNotFoundException("El vehiculo no ha sido encontrado " + deviceId);

    }

    private void handlerValidateError(DataType dataType, @NotNull(message = "Value is required.") String value) {
        throw new BadRequestValidateValueException("El valor ".concat(value).concat("no coincide con el tipo de valor").concat(dataType.name()));
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
        Long sensorId = sensorValidationConfig.getSensorId();
        ExpectedSensors sensors = expectedSensorsRepository.findByDeviceIdAndSensorId(deviceId,sensorId);
        if (!vehicleRepository.checkPermission(userId,deviceId)){handlerNotAccess(deviceId);}
        if (validateDataType(sensors.getDataType(), sensorValidationConfig.getValidatorTime().getStateValidation())){handlerValidateError(sensors.getDataType(),sensorValidationConfig.getValidatorTime().getStateValidation());}
        if(!sensorDeviceRepository.existSensorByDeviceIdAndSensorId(sensorValidationConfig.getDeviceId(),sensors.getNameSensor())){
            String stateCurrent = "";
            switch (sensors.getDataType()){
                case INT -> stateCurrent = "0";
                case TEXT -> stateCurrent = "";
                case DOUBLE -> stateCurrent= "0.0";
            }
            sensorDeviceRepository.create(SensorDevice.builder()
                            .deviceId(deviceId)
                            .timeAcumulated(0L)
                            .initStateCurrent(Instant.now())
                            .sensorName(sensors.getNameSensor())
                            .stateCurrent(stateCurrent)
                            .dataType(sensors.getDataType())
                            .typeSensor(sensors.getTypeSensor())
                    .build());
        }
        String value = JsonUtils.toJson(sensorValidationConfig.getValidatorTime());
        return sensorValidatorRepository.create(SensorValidationConfig.builder()
                .typeSensor(sensors.getTypeSensor())
                .state(true)
                .typeValidation(TypeValidation.TIME)
                .nameValidation(sensorValidationConfig.getNameValidation())
                .userId(userId)
                .companyId(companyId)
                .deviceId(sensorValidationConfig.getDeviceId())
                .nameSensor(sensors.getNameSensor())
                .value(value)
                .operator(sensorValidationConfig.getOperator())
                .dataType(DataType.INT)
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
    public SensorValidationConfig createValidatorRange(Long userId, CreateSensorRangeValidationConfigDTO dto) {
        User user = userRepository.findByUserId(userId);
        Long companyId = user.getCompanyId().getCompanyId();
        Long deviceId = dto.getDeviceId();
        Long sensorId = dto.getSensorId();
        ExpectedSensors sensors = expectedSensorsRepository.findByDeviceIdAndSensorId(deviceId,sensorId);
        if (!vehicleRepository.checkPermission(userId,deviceId)){
            throw  new EntityNotFoundException("El vehiculo no ha sido encontrado " + deviceId);
        }
        return sensorValidatorRepository.create(SensorValidationConfig.builder()
                        .typeSensor(sensors.getTypeSensor())
                        .state(true)
                        .companyId(companyId)
                        .userId(userId)
                        .nameValidation(dto.getNameValidation())
                        .typeValidation(TypeValidation.RANGE)
                        .value(JsonUtils.toJson(dto.getValue()))
                        .deviceId(deviceId)
                        .nameSensor(sensors.getNameSensor())
                        .dataType(DataType.DOUBLE)
                        .operator("=")
                        .messageAlert(dto.getMessageAlert())
                .build());
    }

    @Override
    public SensorValidationConfig updateValidatorRange(Long userId, Long resourceId, UpdateSensorRangeValidationConfigDTO dto) {
        return null;
    }

    @Override
    public SensorValidationConfig createValidatorExist(Long userId, CreateSensorExistValidationConfigDTO dto) {
        User user = userRepository.findByUserId(userId);
        Long companyId = user.getCompanyId().getCompanyId();
        Long deviceId = dto.getDeviceId();
        Long sensorId = dto.getSensorId();
        ExpectedSensors sensors = expectedSensorsRepository.findByDeviceIdAndSensorId(deviceId,sensorId);
        if (!vehicleRepository.checkPermission(userId,deviceId)){
            throw  new EntityNotFoundException("El vehiculo no ha sido encontrado " + deviceId);
        }
        return sensorValidatorRepository.create(SensorValidationConfig.builder()
                .typeSensor(sensors.getTypeSensor())
                .state(true)
                .companyId(companyId)
                .userId(userId)
                .nameValidation(dto.getNameValidation())
                .typeValidation(TypeValidation.EXIST)
                .value("")
                .deviceId(deviceId)
                .nameSensor(sensors.getNameSensor())
                .dataType(sensors.getDataType())
                .operator("=")
                .messageAlert(dto.getMessageAlert())
                .build());
    }

    @Override
    public SensorValidationConfig updateValidatorTime(Long userId, Long resourceId, UpdateSensorExistValidationConfigDTO sensorValidationConfig) {
        return null;
    }


    @Override
    public void deleteByUserIdAndResourceId(Long userId, Long resourceId){
        sensorValidatorRepository.deleteResourceId(resourceId,userId);
    }
    @Override
    public List<ResponseSensorValidatorConfig> findAllByDeviceId(Long userId, Long deviceId){
        return sensorValidatorRepository.findAllByUserIdAndDeviceId(userId,deviceId);
    }
    private boolean validateDataType(DataType expectedType, String value) {
        try {
            switch (expectedType) {
                case INT:
                    Integer.parseInt(value);
                    break;
                case DOUBLE:
                    Double.parseDouble(value);
                    break;
                case TEXT:
                    // Para TEXT, cualquier cadena es válida, no es necesaria conversión.
                    break;
                default:
                    throw new IllegalArgumentException("Tipo de dato no soportado: " + expectedType);
            }
            return false;
        } catch (NumberFormatException e) {
            return true; // El valor no es válido para el tipo esperado.
        }
    }

}
