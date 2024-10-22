package com.labotec.traccar.infra.web.controller.rest.labotec;

import com.labotec.traccar.app.usecase.ports.out.VehicleService;
import com.labotec.traccar.domain.database.models.Vehicle;
import com.labotec.traccar.domain.web.dto.VehicleDTO;
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
    public ResponseEntity<Vehicle> create(@RequestBody @Valid VehicleDTO vehicleDTO) {
        Vehicle createdVehicle = vehicleService.create(vehicleDTO);
        return ResponseEntity.ok(createdVehicle);
    }

    // Endpoint para obtener un vehículo por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> findById(@PathVariable @NotNull Integer id) {
        Optional<Vehicle> result = Optional.ofNullable(vehicleService.findById(id));
        return result.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para obtener todos los vehículos
    @GetMapping("")
    public ResponseEntity<Iterable<Vehicle>> findAll() {
        Iterable<Vehicle> allVehicles = vehicleService.findAll();
        return ResponseEntity.ok(allVehicles);
    }

    // Endpoint para actualizar un vehículo existente
    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> update(@RequestBody @Valid VehicleDTO vehicleDTO, @PathVariable @NotNull Integer id) {
        Vehicle updatedVehicle = vehicleService.update(vehicleDTO, id);
        return ResponseEntity.ok(updatedVehicle);
    }

    // Endpoint para eliminar un vehículo por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable @NotNull Integer id) {
        vehicleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
