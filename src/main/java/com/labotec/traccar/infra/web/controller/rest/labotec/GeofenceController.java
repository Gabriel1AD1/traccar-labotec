package com.labotec.traccar.infra.web.controller.rest.labotec;

import com.labotec.traccar.app.ports.out.services.GeofencePoligonalService;
import com.labotec.traccar.domain.database.models.CircularGeofence;
import com.labotec.traccar.domain.web.labotec.request.create.CircularGeofenceDTO;
import com.labotec.traccar.domain.web.labotec.request.update.GeofencePoligonalUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.labotec.traccar.infra.web.controller.common.API_VERSION_MANAGER.API_VERSION_V1;

@RestController
@RequestMapping(value = API_VERSION_V1 + "geofence")
@AllArgsConstructor
public class GeofenceController {
    private final GeofencePoligonalService poligonalService;

    // Endpoint para crear una nueva geo cerca
    @PostMapping("/circular")
    @Operation(
            summary = "Crear una nueva geo cerca circular",
            description = "Este endpoint permite crear una nueva geo cerca de tipo circular proporcionando la información necesaria. El recurso creado es retornado como un objeto JSON."
    )
    public ResponseEntity<CircularGeofence> create(
            @RequestBody @Valid CircularGeofenceDTO locationDTO,
            @RequestHeader(name = "userId") Long userId) {
        CircularGeofence createdLocation = poligonalService.create(locationDTO, userId);
        return ResponseEntity.ok(createdLocation);
    }

    // Endpoint para obtener una geo cerca por su ID
    @GetMapping("/circular/{id}")
    @Operation(
            summary = "Obtener una geo cerca circular por ID",
            description = "Este endpoint permite obtener los detalles de una geo cerca circular utilizando su ID. Si la geo cerca no existe, se retornará un estado 404."
    )
    public ResponseEntity<CircularGeofence> findById(
            @PathVariable("id") @NotNull Long resourceId,
            @RequestHeader(name = "userId") Long userId) {

        Optional<CircularGeofence> result = Optional.ofNullable(poligonalService.findById(resourceId, userId));
        return result.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para obtener todas las geo cerca
    @GetMapping("/circular")
    @Operation(
            summary = "Obtener todas las geo cerca circulares",
            description = "Este endpoint permite obtener una lista de todas las geo cerca de tipo circular asociadas al usuario."
    )
    public ResponseEntity<Iterable<CircularGeofence>> findAll(@RequestHeader(name = "userId") Long userId) {
        Iterable<CircularGeofence> allLocations = poligonalService.findAll(userId);
        return ResponseEntity.ok(allLocations);
    }

    // Endpoint para actualizar una geo cerca existente
    @PutMapping("/circular/{id}")
    @Operation(
            summary = "Actualizar una geo cerca circular",
            description = "Este endpoint permite actualizar los detalles de una geo cerca circular existente utilizando su ID. Se debe proporcionar la nueva información de la geo cerca."
    )
    public ResponseEntity<CircularGeofence> update(
            @RequestBody @Valid GeofencePoligonalUpdateDTO locationDTO,
            @PathVariable("id") @NotNull Long resourceId,
            @RequestHeader("userId") Long userId
    ) {
        CircularGeofence updatedLocation = poligonalService.update(locationDTO, resourceId, userId);
        return ResponseEntity.ok(updatedLocation);
    }

    // Endpoint para eliminar una geo cerca por su ID
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar una geo cerca por ID",
            description = "Este endpoint permite eliminar una geo cerca utilizando su ID. Si la eliminación es exitosa, se retornará un estado 204."
    )
    public ResponseEntity<Void> deleteById(
            @PathVariable("id") @NotNull Long resourceId,
            @RequestHeader("userId") Long userId
    ) {
        poligonalService.deleteById(resourceId, userId);
        return ResponseEntity.noContent().build();
    }
}
