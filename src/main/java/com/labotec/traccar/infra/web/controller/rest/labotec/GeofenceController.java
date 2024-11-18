package com.labotec.traccar.infra.web.controller.rest.labotec;

import com.labotec.traccar.app.usecase.ports.out.GeofencePoligonalService;
import com.labotec.traccar.domain.database.models.CircularGeofence;
import com.labotec.traccar.domain.web.dto.entel.create.CircularGeofenceDTO;
import com.labotec.traccar.domain.web.dto.entel.update.GeofencePoligonalUpdateDTO;
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
    @PostMapping("")
    public ResponseEntity<CircularGeofence> create(
            @RequestBody @Valid CircularGeofenceDTO locationDTO,
            @RequestHeader(name = "userId") Long userId) {
        CircularGeofence createdLocation = poligonalService.create(locationDTO,userId);
        return ResponseEntity.ok(createdLocation);
    }

    // Endpoint para obtener una geo cerca por su ID
    @GetMapping("/{id}")
    public ResponseEntity<CircularGeofence> findById(
            @PathVariable("id") @NotNull Long resourceId,
            @RequestHeader(name = "userId") Long userId) {

        Optional<CircularGeofence> result = Optional.ofNullable(poligonalService.findById(resourceId,userId));
        return result.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para obtener todas las geo cerca
    @GetMapping("")
    public ResponseEntity<Iterable<CircularGeofence>> findAll(@RequestHeader(name = "userId") Long userId) {
        Iterable<CircularGeofence> allLocations = poligonalService.findAll(userId);
        return ResponseEntity.ok(allLocations);
    }

    // Endpoint para actualizar una geo cerca existente
    @PutMapping("/{id}")
    public ResponseEntity<CircularGeofence> update(
            @RequestBody @Valid GeofencePoligonalUpdateDTO locationDTO,
            @PathVariable("id") @NotNull Long resourceId,
            @RequestHeader("userId" )Long userId

    ) {
        CircularGeofence updatedLocation = poligonalService.update(locationDTO, resourceId,userId);
        return ResponseEntity.ok(updatedLocation);
    }

    // Endpoint para eliminar una geo cerca por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable("id") @NotNull Long resourceId,
            @RequestHeader("userId" )Long userId
    ) {
        poligonalService.deleteById(resourceId,userId);
        return ResponseEntity.noContent().build();
    }
}
