package com.labotec.traccar.infra.web.controller.rest.labotec;

import com.labotec.traccar.app.ports.out.services.SensorValidatorService;
import com.labotec.traccar.domain.web.labotec.request.create.*;
import com.labotec.traccar.domain.web.labotec.request.update.*;
import com.labotec.traccar.domain.web.labotec.response.ResponseSensorValidatorConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static com.labotec.traccar.infra.web.controller.common.API_VERSION_MANAGER.API_VERSION_V1;

@RestController
@RequestMapping(value = API_VERSION_V1 + "validator-sensor")
@AllArgsConstructor
@Tag(name = "Sensor Validator", description = "API for managing sensor validation configurations")
public class SensorValidatorController {

    private final SensorValidatorService sensorValidatorService;

    @GetMapping("/{deviceId}")
    @Operation(
            summary = "Get all validation configurations for a device",
            description = "Retrieves all sensor validation configurations associated with a specific device ID and user ID."
    )
    public ResponseEntity<List<ResponseSensorValidatorConfig>> findAllByDevice(
            @RequestHeader("userId") @NotNull
            @Parameter(description = "User ID making the request", required = true) Long userId,
            @PathVariable("deviceId") @NotNull
            @Parameter(description = "Device ID to retrieve validation configurations for", required = true) Long deviceId
    ) {
        return ResponseEntity.ok(sensorValidatorService.findAllByDeviceId(userId, deviceId));
    }

    @PostMapping("/static")
    @Operation(
            summary = "Create a static sensor validation configuration",
            description = "Creates a new static validation configuration for a sensor."
    )
    public ResponseEntity<Void> createValidatorStaticSensor(
            @RequestHeader("userId") @NotNull
            @Parameter(description = "User ID making the request", required = true) Long userId,
            @RequestBody @Valid
            @Parameter(description = "Details of the static validation configuration", required = true)
            CreateSensorStaticValidationConfigDTO dto
    ) {
        Long resourceId = sensorValidatorService.createValidatorStatic(userId, dto).getId();
        return ResponseEntity.created(URI.create("/api/validator/sensor/static/" + resourceId)).build();
    }

    @PostMapping("/time")
    @Operation(
            summary = "Create a time-based sensor validation configuration",
            description = "Creates a new time-based validation configuration for a sensor."
    )
    public ResponseEntity<Void> createValidatorTimeSensor(
            @RequestHeader("userId") @NotNull
            @Parameter(description = "User ID making the request", required = true) Long userId,
            @RequestBody @Valid
            @Parameter(description = "Details of the time-based validation configuration", required = true)
            CreateSensorTimeValidationConfigDTO dto
    ) {
        Long resourceId = sensorValidatorService.createValidatorTime(userId, dto).getId();
        return ResponseEntity.created(URI.create("/api/validator/sensor/time/" + resourceId)).build();
    }

    @PostMapping("/exist")
    @Operation(
            summary = "Create an existence-based sensor validation configuration",
            description = "Creates a new existence validation configuration for a sensor."
    )
    public ResponseEntity<Void> createValidatorExist(
            @RequestHeader("userId") @NotNull
            @Parameter(description = "User ID making the request", required = true) Long userId,
            @RequestBody @Valid
            @Parameter(description = "Details of the existence validation configuration", required = true)
            CreateSensorExistValidationConfigDTO dto
    ) {
        Long resourceId = sensorValidatorService.createValidatorExist(userId, dto).getId();
        return ResponseEntity.created(URI.create("/api/validator/sensor/exist/" + resourceId)).build();
    }

    @PostMapping("/range")
    @Operation(
            summary = "Create a range-based sensor validation configuration",
            description = "Creates a new range validation configuration for a sensor."
    )
    public ResponseEntity<Void> createValidatorRange(
            @RequestHeader("userId") @NotNull
            @Parameter(description = "User ID making the request", required = true) Long userId,
            @RequestBody @Valid
            @Parameter(description = "Details of the range validation configuration", required = true)
            CreateSensorRangeValidationConfigDTO dto
    ) {
        Long resourceId = sensorValidatorService.createValidatorRange(userId, dto).getId();
        return ResponseEntity.created(URI.create("/api/validator/sensor/range/" + resourceId)).build();
    }

    @PutMapping("/time/{id}")
    @Operation(
            summary = "Update a time-based sensor validation configuration",
            description = "Updates an existing time-based validation configuration for a sensor."
    )
    public ResponseEntity<Void> updateValidatorTimeSensor(
            @RequestHeader("userId") @NotNull
            @Parameter(description = "User ID making the request", required = true) Long userId,
            @PathVariable("id") @NotNull
            @Parameter(description = "ID of the validation configuration to update", required = true) Long resourceId,
            @RequestBody @Valid
            @Parameter(description = "Updated details of the time-based validation configuration", required = true)
            UpdateSensorTimeValidationConfigDTO dto
    ) {
        sensorValidatorService.updateValidatorTime(userId, resourceId, dto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/static/{id}")
    @Operation(
            summary = "Update a static sensor validation configuration",
            description = "Updates an existing static validation configuration for a sensor."
    )
    public ResponseEntity<Void> updateValidatorStaticSensor(
            @RequestHeader("userId") @NotNull
            @Parameter(description = "User ID making the request", required = true) Long userId,
            @PathVariable("id") @NotNull
            @Parameter(description = "ID of the validation configuration to update", required = true) Long resourceId,
            @RequestBody @Valid
            @Parameter(description = "Updated details of the static validation configuration", required = true)
            UpdateSensorStaticValidationConfigDTO dto
    ) {
        sensorValidatorService.updateValidatorStatic(userId, resourceId, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a sensor validation configuration",
            description = "Deletes a sensor validation configuration by ID."
    )
    public ResponseEntity<Void> deleteResource(
            @RequestHeader("userId") @NotNull
            @Parameter(description = "User ID making the request", required = true) Long userId,
            @PathVariable("id") @NotNull
            @Parameter(description = "ID of the validation configuration to delete", required = true) Long resourceId
    ) {
        sensorValidatorService.deleteByUserIdAndResourceId(userId, resourceId);
        return ResponseEntity.ok().build();
    }
}
