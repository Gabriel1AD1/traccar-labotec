package com.labotec.traccar.infra.web.controller.rest.labotec;

import com.labotec.traccar.app.ports.out.BusStopService;
import com.labotec.traccar.domain.database.models.BusStop;
import com.labotec.traccar.domain.database.models.BusStopUpdateListDTO;
import com.labotec.traccar.domain.web.dto.labotec.request.create.BusStopCreateDTO;
import com.labotec.traccar.domain.web.dto.labotec.request.update.BusStopUpdateDTO;
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

    // Inyección del servicio específico de BusStop
    private final BusStopService busStopService;
    private final HttpHeaders headers = new HttpHeaders();

    // Endpoint para crear una nueva parada de autobús
    @PostMapping("")
    public ResponseEntity<BusStop> create(
            @RequestHeader(name = "userId") Long userId,
            @RequestBody @Valid BusStopCreateDTO busStopCreateDTO) {
        Long resourceId = busStopService.create(busStopCreateDTO,userId).getId();
        headers.setLocation(URI.create("/api/v1/bus-stop/"+resourceId));
        return ResponseEntity.status(CREATED).headers(headers).build();
    }
    @PostMapping("/list")
    public ResponseEntity<List<Long>> createBusStopList(
            @RequestHeader(name = "userId") Long userId,
            @RequestBody @Valid List<BusStopCreateDTO> busStopCreateDTO) {
        List<Long> createdBusStop = busStopService.createBusStopList(busStopCreateDTO,userId);
        return ResponseEntity.status(CREATED).body(createdBusStop);
    }

    // Endpoint para obtener una parada de autobús por su ID
    @GetMapping("/{id}")
    public ResponseEntity<BusStop> findById(@PathVariable("id") @NotNull Long resourceId,
                                            @RequestHeader(name = "userId") Long userId) {
        Optional<BusStop> result = Optional.ofNullable(busStopService.findById(resourceId,userId));
        return result.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para obtener todas las paradas de autobús
    @GetMapping("")
    public ResponseEntity<Iterable<BusStop>> findAll(@RequestHeader(name = "userId") Long userId) {
        Iterable<BusStop> allBusStops = busStopService.findAll(userId);
        return ResponseEntity.ok(allBusStops);
    }

    // Endpoint para actualizar una parada de autobús existente
    @PutMapping("/{id}")
    public ResponseEntity<BusStop> update(
            @RequestBody @Valid BusStopUpdateDTO busStopDTO,
            @PathVariable("id") @NotNull Long resourceId,
            @RequestHeader(name = "userId") Long userId) {
        BusStop updatedBusStop = busStopService.update(busStopDTO, resourceId ,userId);
        return ResponseEntity.ok(updatedBusStop);
    }
    // Endpoint para actualizar una parada de autobús existente en listas
    @PutMapping("/list")
    public ResponseEntity<Void> updateToList(
            @RequestBody @Valid List<BusStopUpdateListDTO> busStopDTO,
            @RequestHeader(name = "userId") Long userId) {
        busStopService.updateListBusStop(busStopDTO, userId);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    // Endpoint para eliminar una parada de autobús por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(
            @PathVariable("id") @NotNull Long resourceId,
             @RequestHeader(name = "userId") Long userId)
    {
        busStopService.deleteById(resourceId,userId);
        return ResponseEntity.noContent().build();
    }
}
