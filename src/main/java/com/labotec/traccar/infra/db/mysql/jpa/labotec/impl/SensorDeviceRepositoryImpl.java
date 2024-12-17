package com.labotec.traccar.infra.db.mysql.jpa.labotec.impl;

import com.labotec.traccar.app.ports.input.repository.SensorDeviceRepository;
import com.labotec.traccar.domain.database.models.SensorDevice;
import com.labotec.traccar.domain.web.labotec.response.ResponseSensorDevice;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.SensorDeviceEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.SensorDeviceMapper;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.projection.ResponseSensorDeviceProjection;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.SensorDeviceEntityRepositoryJpa;
import com.labotec.traccar.app.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class SensorDeviceRepositoryImpl implements SensorDeviceRepository {
    private final SensorDeviceEntityRepositoryJpa sensorDeviceRepositoryJpa;
    private final SensorDeviceMapper sensorDeviceMapper;
    private final Logger logger = LoggerFactory.getLogger(SensorDeviceRepositoryImpl.class);
    @Override
    public SensorDevice updateAndReturn(Long deviceId, String nameSensor, String stateCurrent) {
        SensorDeviceEntity sensorDeviceEntity = sensorDeviceRepositoryJpa.updateSensorStateAndReturn(deviceId,nameSensor,stateCurrent).orElseThrow(
                () -> new EntityNotFoundException("Sensor no encontrado por el device id : "+ deviceId)
        );
        return sensorDeviceMapper.toModel(sensorDeviceEntity);
    }

    @Override
    public SensorDevice create(SensorDevice sensorDevice) {
        SensorDeviceEntity sensorDeviceEntity = sensorDeviceMapper.toEntity(sensorDevice);
        return sensorDeviceMapper.toModel(sensorDeviceRepositoryJpa.save(sensorDeviceEntity));
    }

    @Override
    public SensorDevice update(SensorDevice sensorDevice) {
        SensorDeviceEntity sensorDeviceEntity = sensorDeviceMapper.toEntity(sensorDevice);
        return sensorDeviceMapper.toModel(sensorDeviceRepositoryJpa.save(sensorDeviceEntity));
    }

    @Override
    public void deleteByDeviceId(Long deviceId, Long resourceId) {
        int arrowAffected = sensorDeviceRepositoryJpa.deleteByDeviceIdAndId(deviceId,resourceId);
        if (arrowAffected >0){
            logger.info("Se elimino correctamente el sensor con el id {}", resourceId);
        }else {
            logger.warn("No se logro eliminar el sensor");
            throw new EntityNotFoundException("El sensor que ha deseado eliminar no existe "+resourceId);
        }
    }

    @Override
    public List<ResponseSensorDevice> findAllByDevice(Long deviceId) {
        // Obtener la lista de proyecciones desde el repositorio
        List<ResponseSensorDeviceProjection> projectionList = sensorDeviceRepositoryJpa.findByDeviceId(deviceId);

        // Mapear la lista de proyecciones a la lista de ResponseSensorDevice usando el builder de Lombok
        return projectionList.stream()
                .map(projection -> ResponseSensorDevice.builder()
                        .sensorName(projection.getSensorName())
                        .stateCurrent(projection.getStateCurrent())
                        .typeSensor(projection.getTypeSensor())
                        .initStateCurrent(projection.getInitStateCurrent())
                        .timeAcumulated(projection.getTimeAcumulated())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public boolean existSensorByDeviceIdAndSensorId(Long deviceId, String sensorName) {
        return sensorDeviceRepositoryJpa.existsByDeviceIdAndSensorName(deviceId,sensorName);
    }

}
