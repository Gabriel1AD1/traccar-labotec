package com.labotec.traccar.app.implementation;

import com.labotec.traccar.app.ports.input.repository.SensorDeviceRepository;
import com.labotec.traccar.app.ports.input.repository.VehicleRepository;
import com.labotec.traccar.app.ports.out.services.SensorDeviceService;
import com.labotec.traccar.domain.database.models.SensorDevice;
import com.labotec.traccar.domain.web.dto.labotec.request.create.CreateSensorDeviceDTO;
import com.labotec.traccar.infra.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@AllArgsConstructor
@Service
public class SensorDeviceServiceI implements SensorDeviceService {
    private final SensorDeviceRepository sensorDeviceRepository;
    private final VehicleRepository vehicleRepository;
    @Override
    public SensorDevice createSensor(Long userId, CreateSensorDeviceDTO sensorDeviceDTO) {

        checkPermission(userId, sensorDeviceDTO.getDeviceId());

        String stateCurrent = "";
        Instant initStateCurrent = Instant.now();
        switch (sensorDeviceDTO.getDataType()){
            case INT -> stateCurrent = "0";
            case TEXT -> stateCurrent = "";
            case DOUBLE -> stateCurrent= "0.0";
        }
        System.out.println(stateCurrent);
        return sensorDeviceRepository.create(SensorDevice.builder()
                        .typeSensor(sensorDeviceDTO.getTypeSensor())
                        .sensorName(sensorDeviceDTO.getSensorName())
                        .deviceId(sensorDeviceDTO.getDeviceId())
                        .stateCurrent(stateCurrent)
                        .timeAcumulated(0L)
                        .dataType(sensorDeviceDTO.getDataType())
                        .initStateCurrent(initStateCurrent)
                .build());
    }

    @Override
    public void deleteByResourceId(Long deviceId, Long resourceId, Long userId){
        checkPermission(deviceId,userId);
        sensorDeviceRepository.deleteByDeviceId(deviceId,resourceId);

    }
    private void checkPermission(Long userId , Long deviceId){
        if(!vehicleRepository.checkPermission(userId,deviceId)){
            throw  new EntityNotFoundException("La entidad no ha sido encontrada");
        }
    }
}
