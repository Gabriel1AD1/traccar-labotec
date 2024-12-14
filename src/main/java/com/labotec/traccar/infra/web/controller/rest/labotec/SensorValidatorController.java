package com.labotec.traccar.infra.web.controller.rest.labotec;

import com.labotec.traccar.app.ports.out.services.SensorValidatorService;
import com.labotec.traccar.domain.web.dto.labotec.request.create.CreateSensorStaticValidationConfigDTO;
import com.labotec.traccar.domain.web.dto.labotec.request.create.CreateSensorTimeValidationConfigDTO;
import com.labotec.traccar.domain.web.dto.labotec.request.update.UpdateSensorStaticValidationConfigDTO;
import com.labotec.traccar.domain.web.dto.labotec.request.update.UpdateSensorTimeValidationConfigDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static com.labotec.traccar.infra.web.controller.common.API_VERSION_MANAGER.API_VERSION_V1;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(value = API_VERSION_V1 + "validator/sensor")
@AllArgsConstructor
public class SensorValidatorController {
    private final SensorValidatorService sensorValidatorService;

    @PostMapping("/static")
    public ResponseEntity<Void> createValidatorStaticSensor(
            @RequestHeader("userId")Long userId,
            @RequestBody CreateSensorStaticValidationConfigDTO dto ){
        Long resourceId = sensorValidatorService.createValidatorStatic(userId,dto).getId();
        return ResponseEntity.created(URI.create("/api/validator/sensor/static/"+resourceId)).build();
    }
    @PostMapping("/time")
    public ResponseEntity<Void> createValidatorTimeSensor(
            @RequestHeader("userId")Long userId,
            @RequestBody @Valid CreateSensorTimeValidationConfigDTO dto){

        Long resourceId = sensorValidatorService.createValidatorTime(userId,dto).getId();

        return ResponseEntity.created(URI.create("/api/validator/sensor/time/"+resourceId)).build();
    }
    @PutMapping("/time/{id}")
    public ResponseEntity<Void> updateValidatorTimeSensor(
            @RequestHeader("userId")Long userId,
            @PathVariable("id")Long resourceId,
            @RequestBody @Valid UpdateSensorTimeValidationConfigDTO dto
            )
    {
        sensorValidatorService.updateValidatorTime(userId,resourceId,dto);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/static/{id}")
    public ResponseEntity<Void> updateValidatorStaticSensor(
            @RequestHeader("userId")Long userId,
            @PathVariable("id")Long resourceId,
            @RequestBody @Valid UpdateSensorStaticValidationConfigDTO dto
    )
    {
        sensorValidatorService.updateValidatorStatic(userId,resourceId,dto);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResource(
            @RequestHeader("userId")Long userId,
            @PathVariable("id") Long resourceId
    ){
        sensorValidatorService.deleteByUserIdAndResourceId(userId,resourceId);
        return ResponseEntity.ok().build();
    }
}
