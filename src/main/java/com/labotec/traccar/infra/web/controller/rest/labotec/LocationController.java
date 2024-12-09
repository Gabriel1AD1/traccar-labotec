package com.labotec.traccar.infra.web.controller.rest.labotec;

import com.labotec.traccar.app.ports.out.LocationService;
import com.labotec.traccar.domain.database.models.Location;
import com.labotec.traccar.domain.web.dto.labotec.request.create.LocationDTO;
import com.labotec.traccar.domain.web.dto.labotec.request.update.LocationUpdateDTO;
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
@RequestMapping(value = API_VERSION_V1 + "location")
@AllArgsConstructor
public class LocationController {

    // Inyección del servicio específico de Location
    private final LocationService locationService;

    // Endpoint para crear una nueva ubicación
    @PostMapping("")
    public ResponseEntity<Void> create(
            @RequestBody @Valid LocationDTO locationDTO,
            @RequestHeader(name = "userId") Long userId) {
        Long createdLocation = locationService.create(locationDTO,userId).getId();
        HttpHeaders httpHeaders = new  HttpHeaders();
        httpHeaders.setLocation(URI.create("/api/v1/location/"+ createdLocation));
        return ResponseEntity.status(CREATED).headers(httpHeaders).build();
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
