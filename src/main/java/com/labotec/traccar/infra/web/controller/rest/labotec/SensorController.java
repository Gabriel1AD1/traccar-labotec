package com.labotec.traccar.infra.web.controller.rest.labotec;

import com.labotec.traccar.app.ports.out.services.SensorDeviceService;
import com.labotec.traccar.domain.web.dto.labotec.request.create.CreateSensorDeviceDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static com.labotec.traccar.infra.web.controller.common.API_VERSION_MANAGER.API_VERSION_V1;

@RestController
@RequestMapping(value = API_VERSION_V1 + "sensor")
@AllArgsConstructor
public class SensorController {
    private final SensorDeviceService sensorDeviceService;
    @PostMapping("")
    public ResponseEntity<Void> createNewSensor(
            @RequestHeader("userId") Long userId,
            @RequestBody CreateSensorDeviceDTO dto){
        Long resourceId = sensorDeviceService.createSensor(userId,dto).getId();
        return ResponseEntity.created(URI.create("/api/sensor/".concat(String.valueOf(resourceId)))).build();
    }
}
