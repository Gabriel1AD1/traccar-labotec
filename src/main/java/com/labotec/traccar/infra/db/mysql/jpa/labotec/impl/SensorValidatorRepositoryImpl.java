package com.labotec.traccar.infra.db.mysql.jpa.labotec.impl;

import com.labotec.traccar.app.ports.input.repository.SensorValidatorRepository;
import com.labotec.traccar.domain.database.models.SensorValidationConfig;
import com.labotec.traccar.domain.web.dto.labotec.response.ResponseSensorValidatorConfig;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.SensorValidationConfigEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.SensorValidationMapper;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.projection.ResponseSensorValidatorConfigProjection;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.SensorValidationConfigEntityRepositoryJpa;
import com.labotec.traccar.infra.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class SensorValidatorRepositoryImpl implements SensorValidatorRepository {
    private final SensorValidationConfigEntityRepositoryJpa repositoryJpa;
    private final SensorValidationMapper sensorValidationMapper;
    private final Logger logger = LoggerFactory.getLogger(SensorValidatorRepositoryImpl.class);
    @Override
    public SensorValidationConfig create(SensorValidationConfig model) {
        SensorValidationConfigEntity entity = repositoryJpa.save(sensorValidationMapper.toEntity(model));
        return sensorValidationMapper.toModel(entity);
    }

    @Override
    public SensorValidationConfig update(SensorValidationConfig model) {
        SensorValidationConfigEntity entity = repositoryJpa.save(sensorValidationMapper.toEntity(model));
        return sensorValidationMapper.toModel(entity);
    }

    @Override
    public void deleteByDeviceId(Long deviceId) {
        int arrowAffected = repositoryJpa.deleteByDeviceId(deviceId);
        if (arrowAffected > 0 ){
            logger.info("Se ha eliminado correctamente los validadores de sensores del dispositivo {}",deviceId);
        }
    }

    @Override
    public void deleteResourceId(Long resourceId, Long userId) {
        int arrowAffected = repositoryJpa.deleteByIdAndUserId(resourceId,userId);
        if (arrowAffected > 0 ){
            logger.info("Se ha eliminado correctamente el validador con el id {}",resourceId);
        }else {
            logger.warn("No se ha logrado eliminar el validador del ");
        }
    }

    @Override
    public SensorValidationConfig findById(Long resourceId, Long userId) {
        SensorValidationConfigEntity sensorValidationConfig = repositoryJpa.findByIdAndUserId(resourceId,userId).orElseThrow(()-> new EntityNotFoundException("La configuración buscada no existe del id "+ resourceId));
        return sensorValidationMapper.toModel(sensorValidationConfig);
    }

    @Override
    public List<ResponseSensorValidatorConfig> findAllByUserIdAndDeviceId(Long userId, Long deviceId) {
        // Obtener la lista de proyecciones desde el repositorio
        List<ResponseSensorValidatorConfigProjection> projections = repositoryJpa.findByDeviceIdAndUserId(deviceId, userId);

        // Mapear las proyecciones a la clase DTO y devolver como una lista
        return projections.stream()
                .map(projection -> ResponseSensorValidatorConfig.builder()
                        .nameSensor(projection.getNameSensor())
                        .operator(projection.getOperator())
                        .value(projection.getValue())
                        .messageAlert(projection.getMessageAlert())
                        .dataType(projection.getDataType())
                        .build())
                .collect(Collectors.toList()); // Recolectar los resultados en una lista
    }
}
