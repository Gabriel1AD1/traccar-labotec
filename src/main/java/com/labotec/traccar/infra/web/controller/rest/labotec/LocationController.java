package com.labotec.traccar.infra.web.controller.rest.labotec;

import com.labotec.traccar.app.usecase.ports.out.LocationService;
import com.labotec.traccar.domain.database.models.Location;
import com.labotec.traccar.domain.web.dto.entel.create.LocationDTO;
import com.labotec.traccar.domain.web.dto.entel.update.LocationUpdateDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.labotec.traccar.infra.web.controller.common.API_VERSION_MANAGER.API_VERSION_V1;

@RestController
@RequestMapping(value = API_VERSION_V1 + "location")
@AllArgsConstructor
public class LocationController {

    // Inyección del servicio específico de Location
    private final LocationService locationService;

    // Endpoint para crear una nueva ubicación
    @PostMapping("")
    public ResponseEntity<Location> create(
            @RequestBody @Valid LocationDTO locationDTO,
            @RequestHeader(name = "userId") Long userId) {
        Location createdLocation = locationService.create(locationDTO,userId);
        return ResponseEntity.ok(createdLocation);
    }

    // Endpoint para obtener una ubicación por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Location> findById(
            @PathVariable("id") @NotNull Long resourceId,
            @RequestHeader(name = "userId") Long userId) {
        Optional<Location> result = Optional.ofNullable(locationService.findById(resourceId,userId));
        return result.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para obtener todas las ubicaciones
    @GetMapping("")
    public ResponseEntity<Iterable<Location>> findAll(@RequestHeader(name = "userId") Long userId) {
        Iterable<Location> allLocations = locationService.findAll(userId);
        return ResponseEntity.ok(allLocations);
    }

    // Endpoint para actualizar una ubicación existente
    @PutMapping("/{id}")
    public ResponseEntity<Location> update(@RequestBody @Valid LocationUpdateDTO locationDTO,
                                           @PathVariable("id") @NotNull Long resourceId,
                                           @RequestHeader(name = "userId") Long userId) {
        Location updatedLocation = locationService.update(locationDTO, resourceId,userId);
        return ResponseEntity.ok(updatedLocation);
    }

    // Endpoint para eliminar una ubicación por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") @NotNull Long resourceId,
                                           @RequestHeader(name = "userId") Long userId) {
        locationService.deleteById(resourceId,userId);
        return ResponseEntity.noContent().build();
    }
}
