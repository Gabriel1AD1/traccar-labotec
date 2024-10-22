package com.labotec.traccar.infra.web.controller.rest.labotec;

import com.labotec.traccar.app.usecase.ports.out.RouteBusStopService;
import com.labotec.traccar.domain.database.models.RouteBusStop;
import com.labotec.traccar.domain.web.dto.RouteBusStopDTO;
import com.labotec.traccar.domain.web.dto.RouteBusUpdateStopDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.labotec.traccar.infra.web.controller.common.API_VERSION_MANAGER.API_VERSION_V1;

@RestController
@RequestMapping(value = API_VERSION_V1 + "route-bus-stop")
@AllArgsConstructor
public class RouteBusStopController {

    private final RouteBusStopService routeBusStopService;


    // Endpoint para crear una nueva route-bus-stop
    @PostMapping()
    public ResponseEntity<Iterable<RouteBusStop>> create(@RequestBody @Valid RouteBusStopDTO locationDTO) {
        Iterable<RouteBusStop> createdLocation = routeBusStopService.createList(locationDTO);
        return ResponseEntity.ok(createdLocation);
    }

    // Endpoint para obtener una route-bus-stop por su ID
    @GetMapping("/{id}")
    public ResponseEntity<RouteBusStop> findById(@PathVariable @NotNull Integer id) {
        Optional<RouteBusStop> result = Optional.ofNullable(routeBusStopService.findById(id));
        return result.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para obtener todas las route-bus-stop
    @GetMapping()
    public ResponseEntity<Iterable<RouteBusStop>> findAll() {
        Iterable<RouteBusStop> allLocations = routeBusStopService.findAll();
        return ResponseEntity.ok(allLocations);
    }

    // Endpoint para actualizar una route-bus-stop existente
    @PutMapping("/{id}")
    //TODO AREGLAR ENDPOINT
    //TODO Agregar create y areglar el update
    public ResponseEntity<RouteBusStop> update(@RequestBody @Valid RouteBusStopDTO locationDTO, @PathVariable @NotNull Integer id) {
        RouteBusStop updatedLocation = routeBusStopService.update(locationDTO, id);
        return ResponseEntity.ok(updatedLocation);
    }
    @PutMapping()
    public ResponseEntity<Iterable<RouteBusStop>> update(@RequestBody @Valid RouteBusUpdateStopDTO locationDTO) {
        Iterable<RouteBusStop> updatedLocation = routeBusStopService.updateToList(locationDTO);
        return ResponseEntity.ok(updatedLocation);
    }

    // Endpoint para eliminar una ubicación por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable @NotNull Integer id) {
        routeBusStopService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint para obtener la ruta del bus por su id
    @GetMapping("/route/{id}")
    public ResponseEntity<Iterable<RouteBusStop>> findByRoute(@PathVariable @NotNull Integer id){
        Iterable<RouteBusStop> route = routeBusStopService.findByRoute(id);
        return ResponseEntity.ok(route);
    }
}
