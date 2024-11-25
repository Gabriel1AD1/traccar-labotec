package com.labotec.traccar.infra.web.controller.rest.labotec;

import com.labotec.traccar.app.ports.out.RouteService;
import com.labotec.traccar.domain.database.models.Route;
import com.labotec.traccar.domain.web.dto.labotec.request.create.RouteCreateDTO;
import com.labotec.traccar.domain.web.dto.labotec.request.update.RouteUpdateDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static com.labotec.traccar.infra.web.controller.common.API_VERSION_MANAGER.API_VERSION_V1;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(value = API_VERSION_V1 + "route")
@AllArgsConstructor
public class RouteController {

    // Inyección del servicio específico de Route
    private final RouteService routeService;

    // Endpoint para crear una nueva ruta
    @PostMapping("")
    public ResponseEntity<Void> create(@Valid @RequestBody RouteCreateDTO routeCreateDTO, @RequestHeader(name = "userId") Long userId) {
        Long createdRoute = routeService.create(routeCreateDTO,userId).getId();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/api/v1/route/"+createdRoute));
        return ResponseEntity.status(CREATED).headers(httpHeaders).build();
    }

    // Endpoint para obtener una ruta por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Route> findById(
            @RequestHeader(name = "userId") Long userId,
            @PathVariable("id") @NotNull Long resourceId
    ) {
        System.out.println(userId);
        return ResponseEntity.ok(routeService.findById(resourceId,userId));
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
