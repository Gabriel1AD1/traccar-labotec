package com.labotec.traccar.infra.web.controller.rest.labotec;

import com.labotec.traccar.app.usecase.ports.out.VehicleService;
import com.labotec.traccar.domain.database.models.Vehicle;
import com.labotec.traccar.domain.web.dto.entel.create.VehicleDTO;
import com.labotec.traccar.domain.web.dto.entel.update.VehicleUpdateDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.labotec.traccar.infra.web.controller.common.API_VERSION_MANAGER.API_VERSION_V1;

@RestController
@RequestMapping(value = API_VERSION_V1 + "vehicle")
@AllArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    // Endpoint para crear un nuevo vehículo
    @PostMapping("")
    public ResponseEntity<Vehicle> create(
            @RequestBody @Valid VehicleDTO vehicleDTO,
            @RequestHeader(name = "userId") Long userId
    ) {
        Vehicle createdVehicle = vehicleService.create(vehicleDTO,userId);
        return ResponseEntity.ok(createdVehicle);
    }

    // Endpoint para obtener un vehículo por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> findById(
            @PathVariable("id") @NotNull Long resourceId,
            @RequestHeader(name = "userId") Long userId
    ) {
        Optional<Vehicle> result = Optional.ofNullable(vehicleService.findById(resourceId,userId));
        return result.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para obtener todos los vehículos
    @GetMapping("")
    public ResponseEntity<Iterable<Vehicle>> findAll(
            @RequestHeader(name = "userId") Long userId
    ) {
        Iterable<Vehicle> allVehicles = vehicleService.findAll(userId);
        return ResponseEntity.ok(allVehicles);
    }

    // Endpoint para actualizar un vehículo existente
    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> update(
            @RequestBody @Valid VehicleUpdateDTO vehicleDTO,
            @PathVariable("id") @NotNull Long resourceId,
            @RequestHeader(name = "userId") Long userId
    ) {
        Vehicle updatedVehicle = vehicleService.update(vehicleDTO, resourceId,userId);
        return ResponseEntity.ok(updatedVehicle);
    }

    // Endpoint para eliminar un vehículo por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") @NotNull Long resourceId,
                                           @RequestHeader(name = "userId") Long userId) {
        vehicleService.deleteById(resourceId,userId);
        return ResponseEntity.noContent().build();
    }
}
