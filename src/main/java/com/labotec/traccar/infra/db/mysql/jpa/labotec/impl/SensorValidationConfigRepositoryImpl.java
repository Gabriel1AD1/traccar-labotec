package com.labotec.traccar.infra.db.mysql.jpa.labotec.impl;

import com.labotec.traccar.app.ports.input.repository.OptimizedSensorValidationConfigRepository;
import com.labotec.traccar.domain.database.models.optimized.OptimizedSensorValidationConfig;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.projection.SensorValidationConfigProjection;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.SensorValidationConfigEntityRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@Repository
public class SensorValidationConfigRepositoryImpl implements OptimizedSensorValidationConfigRepository {
    private final SensorValidationConfigEntityRepositoryJpa configEntityRepository;
    @Override
    public List<OptimizedSensorValidationConfig> findAllByDeviceId(Long deviceId) {
        List<SensorValidationConfigProjection> listConfig = configEntityRepository.findValidationConfigsByDeviceId(deviceId);
        return listConfig.stream()
                .map(s -> OptimizedSensorValidationConfig.builder()
                        .messageError(s.getMessageAlert())
                        .nameSensor(s.getNameSensor())
                        .operator(s.getOperator())
                        .value(s.getValue())
                        .dataType(s.getDataType())
                        .typeValidation(s.getTypeValidation())
                        .build())
                .collect(Collectors.toList());
    }

}
