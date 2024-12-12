package com.labotec.traccar.infra.web.controller.rest.labotec;

import com.labotec.traccar.app.ports.out.RouteService;
import com.labotec.traccar.domain.database.models.Route;
import com.labotec.traccar.domain.web.dto.labotec.request.create.RouteCreateDTO;
import com.labotec.traccar.domain.web.dto.labotec.request.update.RouteUpdateDTO;
import com.labotec.traccar.domain.web.dto.labotec.response.ResponseRoute;
import com.labotec.traccar.domain.web.dto.labotec.response.ResponseRouteBusStopInformation;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static com.labotec.traccar.infra.web.controller.common.API_VERSION_MANAGER.API_VERSION_V1;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(value = API_VERSION_V1 + "route")
@AllArgsConstructor
public class RouteController {

    // Inyección del servicio específico de Route
    private final RouteService routeService;

    // Endpoint para crear una nueva ruta
    @Operation(summary = "Crear una nueva ruta", description = "Crea una nueva ruta y retorna la ubicación de la nueva ruta creada.")
    @PostMapping("")
    public ResponseEntity<Void> create(@Valid @RequestBody RouteCreateDTO routeCreateDTO, @RequestHeader(name = "userId") Long userId) {
        Long createdRoute = routeService.create(routeCreateDTO, userId).getId();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/api/v1/route/" + createdRoute));
        return ResponseEntity.status(CREATED).headers(httpHeaders).build();
    }

    // Endpoint para obtener una ruta por su ID
    @Operation(summary = "Obtener ruta por ID", description = "Devuelve una ruta existente basada en su ID.")
    @GetMapping("/{id}")
    public ResponseEntity<Route> findById(
            @RequestHeader(name = "userId") Long userId,
            @PathVariable("id") @NotNull Long resourceId
    ) {
        return ResponseEntity.ok(routeService.findById(resourceId, userId));
    }

    // Endpoint para obtener todas las rutas
    @Operation(summary = "Obtener todas las rutas", description = "Devuelve una lista de todas las rutas disponibles para un usuario específico.")
    @GetMapping("")
    public ResponseEntity<List<ResponseRoute>> findAll(
            @RequestHeader(name = "userId") Long userId
    ) {
        List<ResponseRoute> allRoutes = routeService.findAllByUserId(userId);
        return ResponseEntity.ok(allRoutes);
    }

    // Endpoint para obtener todas las paradas de bus asociadas a una ruta
    @Operation(summary = "Obtener información de las paradas de bus de una ruta", description = "Devuelve una lista de información de las paradas de bus asociadas a una ruta.")
    @GetMapping("/{id}/bus-stop")
    public ResponseEntity<List<ResponseRouteBusStopInformation>> getInformationRoute(
            @RequestHeader(name = "userId") Long userId,
            @PathVariable("id") Long resourceId) {
        List<ResponseRouteBusStopInformation> allBusStopInformationForRoute = routeService.findAllByRouteId(resourceId, userId);
        return ResponseEntity.ok(allBusStopInformationForRoute);
    }

    // Endpoint para actualizar una ruta existente
    @Operation(summary = "Actualizar ruta", description = "Permite actualizar una ruta existente por su ID.")
    @PutMapping("/{id}")
    public ResponseEntity<Route> update(@RequestBody @Valid RouteUpdateDTO routeDTO,
                                        @PathVariable("id") @NotNull Long resourceId,
                                        @RequestHeader(name = "userId") Long userId) {
        Route updatedRoute = routeService.update(routeDTO, resourceId, userId);
        return ResponseEntity.ok(updatedRoute);
    }

    // Endpoint para eliminar una ruta por su ID
    @Operation(summary = "Eliminar ruta", description = "Permite eliminar una ruta existente por su ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") @NotNull Long resourceId,
                                           @RequestHeader(name = "userId") Long userId) {
        routeService.deleteById(resourceId, userId);
        return ResponseEntity.noContent().build();
    }
}
