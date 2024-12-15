package com.labotec.traccar.infra.web.controller.rest.labotec;

import com.labotec.traccar.app.ports.out.services.BusStopService;
import com.labotec.traccar.domain.database.models.BusStop;
import com.labotec.traccar.domain.database.models.BusStopUpdateListDTO;
import com.labotec.traccar.domain.web.labotec.request.create.BusStopCreateDTO;
import com.labotec.traccar.domain.web.labotec.request.update.BusStopUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static com.labotec.traccar.infra.web.controller.common.API_VERSION_MANAGER.API_VERSION_V1;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping(value = API_VERSION_V1 + "bus-stop")
@AllArgsConstructor
public class BusStopController {

    private final BusStopService busStopService;
    private final HttpHeaders headers = new HttpHeaders();

    // Endpoint para crear una nueva parada de autobús
    @PostMapping("")
    @Operation(
            summary = "Crear una nueva parada de autobús",
            description = "Este endpoint permite crear una nueva parada de autobús proporcionando los detalles necesarios. El recurso creado será retornado con el código de estado 201."
    )
    public ResponseEntity<BusStop> create(
            @RequestHeader(name = "userId") Long userId,
            @RequestBody @Valid BusStopCreateDTO busStopCreateDTO) {
        Long resourceId = busStopService.create(busStopCreateDTO, userId).getId();
        headers.setLocation(URI.create("/api/bus-stop/" + resourceId));
        return ResponseEntity.status(CREATED).headers(headers).build();
    }

    // Endpoint para crear una lista de nuevas paradas de autobús
    @PostMapping("/list")
    @Operation(
            summary = "Crear una lista de nuevas paradas de autobús",
            description = "Este endpoint permite crear múltiples paradas de autobús a la vez, proporcionando una lista de objetos con los datos de las paradas."
    )
    public ResponseEntity<List<Long>> createBusStopList(
            @RequestHeader(name = "userId") Long userId,
            @RequestBody @Valid List<BusStopCreateDTO> busStopCreateDTO) {
        List<Long> createdBusStop = busStopService.createBusStopList(busStopCreateDTO, userId);
        return ResponseEntity.status(CREATED).body(createdBusStop);
    }

    // Endpoint para obtener una parada de autobús por su ID
    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener una parada de autobús por ID",
            description = "Este endpoint permite obtener los detalles de una parada de autobús específica usando su ID. Si la parada no existe, se retornará un estado 404."
    )
    public ResponseEntity<BusStop> findById(@PathVariable("id") @NotNull Long resourceId,
                                            @RequestHeader(name = "userId") Long userId) {
        Optional<BusStop> result = Optional.ofNullable(busStopService.findById(resourceId, userId));
        return result.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para obtener todas las paradas de autobús
    @GetMapping("")
    @Operation(
            summary = "Obtener todas las paradas de autobús",
            description = "Este endpoint permite obtener una lista de todas las paradas de autobús asociadas al usuario, retornando todas las paradas almacenadas."
    )
    public ResponseEntity<Iterable<BusStop>> findAll(@RequestHeader(name = "userId") Long userId) {
        Iterable<BusStop> allBusStops = busStopService.findAll(userId);
        return ResponseEntity.ok(allBusStops);
    }

    // Endpoint para actualizar una parada de autobús existente
    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar una parada de autobús",
            description = "Este endpoint permite actualizar los datos de una parada de autobús existente usando su ID. Se debe proporcionar la nueva información de la parada."
    )
    public ResponseEntity<BusStop> update(
            @RequestBody @Valid BusStopUpdateDTO busStopDTO,
            @PathVariable("id") @NotNull Long resourceId,
            @RequestHeader(name = "userId") Long userId) {
        BusStop updatedBusStop = busStopService.update(busStopDTO, resourceId, userId);
        return ResponseEntity.ok(updatedBusStop);
    }

    // Endpoint para actualizar múltiples paradas de autobús
    @PutMapping("/list")
    @Operation(
            summary = "Actualizar múltiples paradas de autobús",
            description = "Este endpoint permite actualizar múltiples paradas de autobús a la vez. Se debe proporcionar una lista con los detalles de las paradas a actualizar."
    )
    public ResponseEntity<Void> updateToList(
            @RequestBody @Valid List<BusStopUpdateListDTO> busStopDTO,
            @RequestHeader(name = "userId") Long userId) {
        busStopService.updateListBusStop(busStopDTO, userId);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    // Endpoint para eliminar una parada de autobús por su ID
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar una parada de autobús por ID",
            description = "Este endpoint permite eliminar una parada de autobús específica utilizando su ID. Si la eliminación es exitosa, se retornará un estado 204."
    )
    public ResponseEntity<Void> deleteById(
            @PathVariable("id") @NotNull Long resourceId,
            @RequestHeader(name = "userId") Long userId) {
        busStopService.deleteById(resourceId, userId);
        return ResponseEntity.noContent().build();
    }
}
