package com.labotec.traccar.infra.web.controller.rest.labotec;

import com.labotec.traccar.app.ports.out.DriverService;
import com.labotec.traccar.domain.database.models.Driver;
import com.labotec.traccar.domain.web.dto.labotec.request.create.DriverDTO;
import com.labotec.traccar.domain.web.dto.labotec.request.update.DriverUpdateDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

import static com.labotec.traccar.infra.web.controller.common.API_VERSION_MANAGER.API_VERSION_V1;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(value = API_VERSION_V1 + "drivers")
@AllArgsConstructor
public class DriverController {

    // Inyección del servicio específico de Driver
    private final DriverService driverService;
    private final HttpHeaders headers = new HttpHeaders();

    // Endpoint para crear un nuevo conductor
    @PostMapping("")
    public ResponseEntity<Driver> create(
            @RequestBody @Valid DriverDTO driverDTO,
            @RequestHeader(name = "userId") Long userId
    ) {
        Long createdDriver = driverService.create(driverDTO,userId).getId();
        headers.setLocation(URI.create("/api/v1/drivers/"+createdDriver));
        return ResponseEntity.status(CREATED).headers(headers).build();
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
