package com.labotec.traccar.infra.web.controller.rest.labotec;

import com.labotec.traccar.app.usecase.ports.out.LocationService;
import com.labotec.traccar.domain.database.models.Location;
import com.labotec.traccar.domain.web.dto.create.LocationDTO;
import com.labotec.traccar.domain.web.dto.update.LocationUpdateDTO;
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
    public ResponseEntity<Location> create(@RequestBody @Valid LocationDTO locationDTO) {
        Location createdLocation = locationService.create(locationDTO);
        return ResponseEntity.ok(createdLocation);
    }

    // Endpoint para obtener una ubicación por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Location> findById(@PathVariable @NotNull Integer id) {
        Optional<Location> result = Optional.ofNullable(locationService.findById(id));
        return result.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para obtener todas las ubicaciones
    @GetMapping("")
    public ResponseEntity<Iterable<Location>> findAll() {
        Iterable<Location> allLocations = locationService.findAll();
        return ResponseEntity.ok(allLocations);
    }

    // Endpoint para actualizar una ubicación existente
    @PutMapping("/{id}")
    public ResponseEntity<Location> update(@RequestBody @Valid LocationUpdateDTO locationDTO, @PathVariable @NotNull Integer id) {
        Location updatedLocation = locationService.update(locationDTO, id);
        return ResponseEntity.ok(updatedLocation);
    }

    // Endpoint para eliminar una ubicación por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable @NotNull Integer id) {
        locationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
