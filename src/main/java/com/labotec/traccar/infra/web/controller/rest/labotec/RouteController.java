package com.labotec.traccar.infra.web.controller.rest.labotec;

import com.labotec.traccar.app.usecase.ports.out.RouteService;
import com.labotec.traccar.domain.database.models.Route;
import com.labotec.traccar.domain.web.dto.entel.create.RouteDTO;
import com.labotec.traccar.domain.web.dto.entel.update.RouteUpdateDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.labotec.traccar.infra.web.controller.common.API_VERSION_MANAGER.API_VERSION_V1;

@RestController
@RequestMapping(value = API_VERSION_V1 + "route")
@AllArgsConstructor
public class RouteController {

    // Inyección del servicio específico de Route
    private final RouteService routeService;

    // Endpoint para crear una nueva ruta
    @PostMapping("")
    public ResponseEntity<Route> create(
            @Valid @RequestBody RouteDTO routeDTO,
            @RequestHeader(name = "userId") Long userId) {
        System.out.println(routeDTO.toString());
        Route createdRoute = routeService.create(routeDTO,userId);
        return ResponseEntity.ok(createdRoute);
    }

    // Endpoint para obtener una ruta por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Route> findById(
            @PathVariable("id") @NotNull Long resourceId,
            @RequestHeader(name = "userId") Long userId
    ) {
        Optional<Route> result = Optional.ofNullable(routeService.findById(resourceId,userId));
        return result.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para obtener todas las rutas
    @GetMapping("")
    public ResponseEntity<Iterable<Route>> findAll(
            @RequestHeader(name = "userId") Long userId
    ) {
        Iterable<Route> allRoutes = routeService.findAll(userId);
        return ResponseEntity.ok(allRoutes);
    }

    // Endpoint para actualizar una ruta existente
    @PutMapping("/{id}")
    public ResponseEntity<Route> update(@RequestBody @Valid RouteUpdateDTO routeDTO,
                                        @PathVariable("id") @NotNull Long resourceId,
                                        @RequestHeader(name = "userId") Long userId) {
        Route updatedRoute = routeService.update(routeDTO, resourceId,userId);
        return ResponseEntity.ok(updatedRoute);
    }

    // Endpoint para eliminar una ruta por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") @NotNull Long resourceId,
                                           @RequestHeader(name = "userId") Long userId) {
        routeService.deleteById(resourceId,userId);
        return ResponseEntity.noContent().build();
    }
}
