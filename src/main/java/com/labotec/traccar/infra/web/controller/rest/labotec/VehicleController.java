package com.labotec.traccar.infra.web.controller.rest.labotec;

import com.labotec.traccar.app.ports.out.VehicleService;
import com.labotec.traccar.domain.database.models.Vehicle;
import com.labotec.traccar.domain.web.dto.labotec.request.create.VehicleDTO;
import com.labotec.traccar.domain.web.dto.labotec.request.update.VehicleUpdateDTO;
import com.labotec.traccar.domain.web.dto.labotec.response.ResponseVehicle;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.labotec.traccar.infra.web.controller.common.API_VERSION_MANAGER.API_VERSION_V1;

@RestController
@RequestMapping(value = API_VERSION_V1 + "vehicle")
@AllArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    // Endpoint para crear un nuevo vehículo
    @PostMapping("")
    @Operation(
            summary = "Crear un nuevo vehículo",
            description = "Este endpoint permite crear un nuevo vehículo proporcionando la información necesaria. El vehículo creado es retornado como objeto JSON."
    )
    public ResponseEntity<Vehicle> create(
            @RequestBody @Valid VehicleDTO vehicleDTO,
            @RequestHeader(name = "userId") Long userId
    ) {
        Vehicle createdVehicle = vehicleService.create(vehicleDTO, userId);
        return ResponseEntity.ok(createdVehicle);
    }

    // Endpoint para obtener un vehículo por su ID
    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener un vehículo por ID",
            description = "Este endpoint permite obtener un vehículo específico utilizando su ID. Si el vehículo no existe, se retornará un estado 404."
    )
    public ResponseEntity<Vehicle> findById(
            @PathVariable("id") @NotNull Long resourceId,
            @RequestHeader(name = "userId") Long userId
    ) {
        Optional<Vehicle> result = Optional.ofNullable(vehicleService.findById(resourceId, userId));
        return result.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para obtener todos los vehículos
    @GetMapping("")
    @Operation(
            summary = "Obtener todos los vehículos",
            description = "Este endpoint permite obtener la lista de todos los vehículos disponibles para el usuario, basándose en el ID del usuario."
    )
    public ResponseEntity<List<ResponseVehicle>> findAll(
            @RequestHeader(name = "userId") Long userId
    ) {
        List<ResponseVehicle> allVehicles = vehicleService.findAllByUserId(userId);
        return ResponseEntity.ok(allVehicles);
    }

    // Endpoint para actualizar un vehículo existente
    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar un vehículo",
            description = "Este endpoint permite actualizar un vehículo existente. Se debe proporcionar la nueva información del vehículo y su ID."
    )
    public ResponseEntity<Vehicle> update(
            @RequestBody @Valid VehicleUpdateDTO vehicleDTO,
            @PathVariable("id") @NotNull Long resourceId,
            @RequestHeader(name = "userId") Long userId
    ) {
        Vehicle updatedVehicle = vehicleService.update(vehicleDTO, resourceId, userId);
        return ResponseEntity.ok(updatedVehicle);
    }

    // Endpoint para eliminar un vehículo por su ID
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar un vehículo por ID",
            description = "Este endpoint permite eliminar un vehículo específico utilizando su ID. Si el vehículo es eliminado exitosamente, se retorna un estado 204."
    )
    public ResponseEntity<Void> deleteById(@PathVariable("id") @NotNull Long resourceId,
                                           @RequestHeader(name = "userId") Long userId) {
        vehicleService.deleteById(resourceId, userId);
        return ResponseEntity.noContent().build();
    }
}
