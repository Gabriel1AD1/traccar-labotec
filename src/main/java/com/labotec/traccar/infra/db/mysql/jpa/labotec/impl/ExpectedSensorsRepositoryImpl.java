package com.labotec.traccar.infra.db.mysql.jpa.labotec.impl;

import com.labotec.traccar.app.ports.input.repository.ExpectedSensorsRepository;
import com.labotec.traccar.domain.database.models.ExpectedSensors;
import com.labotec.traccar.domain.web.labotec.response.ResponseExpectedSensor;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.ExpectedSensorsEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.ExpectedSensorsMapper;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.projection.ResponseExpectedSensorsProjection;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.ExpectedSensorsEntityRepositoryJpa;
import com.labotec.traccar.app.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class ExpectedSensorsRepositoryImpl implements ExpectedSensorsRepository {
    private final ExpectedSensorsMapper mapper;
    private final ExpectedSensorsEntityRepositoryJpa repositoryJpa;
    private final Logger logger = LoggerFactory.getLogger(ExpectedSensorsRepositoryImpl.class);
    @Override
    public ExpectedSensors create(ExpectedSensors entity) {
        var expectedSensorsEntity = repositoryJpa.save(mapper.toEntity(entity));
        return mapper.toModel(expectedSensorsEntity);
    }

    @Override
    public ExpectedSensors findById(Long resourceId, Long userId) {
        var expectedSensorsEntity = repositoryJpa.findByIdAndUserId(resourceId,userId).orElseThrow(
                () -> new EntityNotFoundException("La entidad no ha sido encontrado")
        );
        return mapper.toModel(expectedSensorsEntity);
    }

    @Override
    public Optional<ExpectedSensors> findByIdOptional(Long resourceId, Long userId) {
        ExpectedSensorsEntity expectedSensorsEntity = repositoryJpa.findByIdAndUserId(resourceId,userId).orElse(null);
        ExpectedSensors expectedSensors = mapper.toModel(expectedSensorsEntity);
        return Optional.ofNullable(expectedSensors);
    }

    @Override
    public Iterable<ExpectedSensors> findAll(Long userId) {
        List<ExpectedSensorsEntity> entityList = repositoryJpa.findAllByUserId(userId);
        return mapper.toModelList(entityList) ;
    }

    @Override
    public ExpectedSensors update(ExpectedSensors entity) {
        var expectedSensorsEntity = repositoryJpa.save(mapper.toEntity(entity));
        return mapper.toModel(expectedSensorsEntity);
    }

    @Override
    public void deleteById(Long resourceId, Long userId) {
        int arrowAffected = repositoryJpa.deleteByIdAndUserId(resourceId,userId);
        if (arrowAffected > 0 ){
            logger.info("se eliminio correctamente el recurso : {}", resourceId);

        }else {
            logger.warn("No se ha logrado eliminar el recurso con id :  {}",resourceId);
            throw new EntityNotFoundException("La recursos que trata de eliminar no existe con id : "+resourceId );
        }
    }
    @Override
    public List<ResponseExpectedSensor> findAllByDeviceId(Long deviceId, Long userId) {
        // Obtén la lista de proyecciones desde el repositorio
        List<ResponseExpectedSensorsProjection> projectionList = repositoryJpa.findAllByDeviceIdAndUserId(deviceId, userId);

        // Usa stream y Collectors para mapear las proyecciones a ResponseExpectedSensor
        return projectionList.stream()
                .map(projection -> ResponseExpectedSensor.builder()
                        .id(projection.getId())
                        .nameSensor(projection.getNameSensor())
                        .descriptionSensor(projection.getDescriptionSensor())
                        .typeSensor(projection.getTypeSensor())
                        .dataType(projection.getDataType())
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    public ExpectedSensors findByDeviceIdAndSensorNameAndUserId(Long deviceId, String name, Long userId) {
        ExpectedSensorsEntity entity = repositoryJpa.findByDeviceIdAndNameSensorAndUserId(deviceId,name,userId).orElseThrow(
                () -> new EntityNotFoundException("El sensor no ha sido encontrado")
        );
        return mapper.toModel(entity);
    }

    @Override
    public ExpectedSensors findByDeviceIdAndSensorId(Long deviceId, Long sensorId) {
        return mapper.toModel(repositoryJpa.findByIdAndDeviceId(sensorId,deviceId).orElseThrow(
                () -> new EntityNotFoundException("La entidad no ha sido encontrada " + sensorId)
        ));
    }

}
