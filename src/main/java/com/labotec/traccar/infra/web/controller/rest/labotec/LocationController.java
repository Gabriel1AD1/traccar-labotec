package com.labotec.traccar.infra.web.controller.rest.labotec;

import com.labotec.traccar.app.ports.out.services.LocationService;
import com.labotec.traccar.domain.database.models.Location;
import com.labotec.traccar.domain.web.labotec.request.create.LocationDTO;
import com.labotec.traccar.domain.web.labotec.request.update.LocationUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
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

    private final LocationService locationService;

    // Endpoint para crear una nueva ubicación
    @PostMapping("")
    @Operation(
            summary = "Crear una nueva ubicación",
            description = "Este endpoint permite crear una nueva ubicación proporcionando los detalles necesarios. La ubicación creada será retornada con un código de estado 201."
    )
    public ResponseEntity<Void> create(
            @RequestBody @Valid LocationDTO locationDTO,
            @RequestHeader(name = "userId") Long userId) {
        Long createdLocation = locationService.create(locationDTO, userId).getId();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/api/location/" + createdLocation));
        return ResponseEntity.status(CREATED).headers(httpHeaders).build();
    }

    // Endpoint para obtener una ubicación por su ID
    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener una ubicación por ID",
            description = "Este endpoint permite obtener los detalles de una ubicación específica utilizando su ID. Si la ubicación no existe, se retornará un estado 404."
    )
    public ResponseEntity<Location> findById(
            @PathVariable("id") @NotNull Long resourceId,
            @RequestHeader(name = "userId") Long userId) {
        Optional<Location> result = Optional.ofNullable(locationService.findById(resourceId, userId));
        return result.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para obtener todas las ubicaciones
    @GetMapping("")
    @Operation(
            summary = "Obtener todas las ubicaciones",
            description = "Este endpoint permite obtener una lista de todas las ubicaciones asociadas al usuario."
    )
    public ResponseEntity<Iterable<Location>> findAll(@RequestHeader(name = "userId") Long userId) {
        Iterable<Location> allLocations = locationService.findAll(userId);
        return ResponseEntity.ok(allLocations);
    }

    // Endpoint para actualizar una ubicación existente
    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar una ubicación existente",
            description = "Este endpoint permite actualizar los detalles de una ubicación existente proporcionando su ID y los nuevos datos."
    )
    public ResponseEntity<Location> update(@RequestBody @Valid LocationUpdateDTO locationDTO,
                                           @PathVariable("id") @NotNull Long resourceId,
                                           @RequestHeader(name = "userId") Long userId) {
        Location updatedLocation = locationService.update(locationDTO, resourceId, userId);
        return ResponseEntity.ok(updatedLocation);
    }

    // Endpoint para eliminar una ubicación por su ID
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar una ubicación por ID",
            description = "Este endpoint permite eliminar una ubicación específica utilizando su ID. Si la eliminación es exitosa, se retornará un estado 204."
    )
    public ResponseEntity<Void> deleteById(@PathVariable("id") @NotNull Long resourceId,
                                           @RequestHeader(name = "userId") Long userId) {
        locationService.deleteById(resourceId, userId);
        return ResponseEntity.noContent().build();
    }
}
