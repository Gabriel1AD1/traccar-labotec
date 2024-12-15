package com.labotec.traccar.app.implementation;

import com.labotec.traccar.app.ports.input.repository.ExpectedSensorsRepository;
import com.labotec.traccar.app.ports.input.repository.UserRepository;
import com.labotec.traccar.app.ports.input.repository.VehicleRepository;
import com.labotec.traccar.app.ports.out.services.ExpectedSensorsService;
import com.labotec.traccar.domain.database.models.ExpectedSensors;
import com.labotec.traccar.domain.database.models.User;
import com.labotec.traccar.domain.web.labotec.request.create.CreateExpectedSensorsDTO;
import com.labotec.traccar.domain.web.labotec.response.ResponseExpectedSensor;
import com.labotec.traccar.app.exception.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ExpectedSensorsServiceI implements ExpectedSensorsService {
    private final ExpectedSensorsRepository repository;
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    @Override
    public ExpectedSensors create(Long userId, CreateExpectedSensorsDTO dto) {
        User user  = userRepository.findByUserId(userId);
        Long companyId = user.getCompanyId().getCompanyId();
        if (!vehicleRepository.checkPermission(userId,dto.getDeviceId())){
            sendNotFound(dto.getDeviceId());
        }
        return repository.create(ExpectedSensors.builder()
                        .userId(userId)
                        .companyId(companyId)
                        .nameSensor(dto.getNameSensor())
                        .descriptionSensor(dto.getDescriptionSensor())
                        .dataType(dto.getDataType())
                        .typeSensor(dto.getTypeSensor())
                        .deviceId(dto.getDeviceId())
                .build());
    }

    private void sendNotFound(@NotNull(message = "Device ID cannot be null.") Long deviceId) {
        throw new EntityNotFoundException("No existe el id del vehiculo " + deviceId);
    }

    @Override
    public List<ResponseExpectedSensor> findAll(Long userId, Long deviceId) {
        return repository.findAllByDeviceId(deviceId, userId);
    }

    @Override
    public void delete(Long userId, Long resourceId) {
        repository.deleteById(resourceId,userId);
    }
}
