package com.labotec.traccar.infra.web.controller.rest.labotec;

import com.labotec.traccar.app.usecase.ports.out.RouteService;
import com.labotec.traccar.domain.database.models.Route;
import com.labotec.traccar.domain.web.dto.RouteDTO;
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
    public ResponseEntity<Route> create(@RequestBody @Valid RouteDTO routeDTO) {
        Route createdRoute = routeService.create(routeDTO);
        return ResponseEntity.ok(createdRoute);
    }

    // Endpoint para obtener una ruta por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Route> findById(@PathVariable @NotNull Integer id) {
        Optional<Route> result = Optional.ofNullable(routeService.findById(id));
        return result.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para obtener todas las rutas
    @GetMapping("")
    public ResponseEntity<Iterable<Route>> findAll() {
        Iterable<Route> allRoutes = routeService.findAll();
        return ResponseEntity.ok(allRoutes);
    }

    // Endpoint para actualizar una ruta existente
    @PutMapping("/{id}")
    public ResponseEntity<Route> update(@RequestBody @Valid RouteDTO routeDTO, @PathVariable @NotNull Integer id) {
        Route updatedRoute = routeService.update(routeDTO, id);
        return ResponseEntity.ok(updatedRoute);
    }

    // Endpoint para eliminar una ruta por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable @NotNull Integer id) {
        routeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
