package com.labotec.traccar.infra.web.controller.rest.labotec;

import com.labotec.traccar.app.usecase.ports.out.DriverService;
import com.labotec.traccar.domain.database.models.Driver;
import com.labotec.traccar.domain.web.dto.entel.create.DriverDTO;
import com.labotec.traccar.domain.web.dto.entel.update.DriverUpdateDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.labotec.traccar.infra.web.controller.common.API_VERSION_MANAGER.API_VERSION_V1;

@RestController
@RequestMapping(value = API_VERSION_V1 + "driver")
@AllArgsConstructor
public class DriverController {

    // Inyección del servicio específico de Driver
    private final DriverService driverService;

    // Endpoint para crear un nuevo conductor
    @PostMapping("")
    public ResponseEntity<Driver> create(
            @RequestBody @Valid DriverDTO driverDTO,
            @RequestHeader(name = "userId") Long userId
    ) {
        Driver createdDriver = driverService.create(driverDTO,userId);
        return ResponseEntity.ok(createdDriver);
    }

    // Endpoint para obtener un conductor por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Driver> findById(
            @PathVariable("id") @NotNull Long resourceId,
            @RequestHeader(name = "userId") Long userId
    ) {
        Optional<Driver> result = Optional.ofNullable(driverService.findById(resourceId,userId));
        return result.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para obtener todos los conductores
    @GetMapping("")
    public ResponseEntity<Iterable<Driver>> findAll(
            @RequestHeader(name = "userId") Long userId
    ) {
        Iterable<Driver> allDrivers = driverService.findAll(userId);
        return ResponseEntity.ok(allDrivers);
    }

    // Endpoint para actualizar un conductor existente
    @PutMapping("/{id}")
    public ResponseEntity<Driver> update(
            @RequestBody @Valid DriverUpdateDTO driverDTO,
            @PathVariable("id") @NotNull Long resourceId,
            @RequestHeader(name = "userId") Long userId

    ) {
        Driver updatedDriver = driverService.update(driverDTO, resourceId,userId);
        return ResponseEntity.ok(updatedDriver);
    }

    // Endpoint para eliminar un conductor por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable("id") @NotNull Long resourceId,
            @RequestHeader(name = "userId") Long userId
    ) {
        driverService.deleteById(resourceId,userId);
        return ResponseEntity.noContent().build();
    }
}
