package com.labotec.traccar.infra.web.controller.rest.labotec;

import com.labotec.traccar.app.usecase.ports.out.BusStopService;
import com.labotec.traccar.domain.database.models.BusStop;
import com.labotec.traccar.domain.web.dto.create.BusStopDTO;
import com.labotec.traccar.domain.web.dto.update.BusStopUpdateDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.labotec.traccar.infra.web.controller.common.API_VERSION_MANAGER.API_VERSION_V1;

@RestController
@RequestMapping(value = API_VERSION_V1 + "bus-stop")
@AllArgsConstructor
public class BusStopController {

    // Inyección del servicio específico de BusStop
    private final BusStopService busStopService;

    // Endpoint para crear una nueva parada de autobús
    @PostMapping("")
    public ResponseEntity<BusStop> create(@RequestBody @Valid BusStopDTO busStopDTO) {
        BusStop createdBusStop = busStopService.create(busStopDTO);
        return ResponseEntity.ok(createdBusStop);
    }

    // Endpoint para obtener una parada de autobús por su ID
    @GetMapping("/{id}")
    public ResponseEntity<BusStop> findById(@PathVariable @NotNull Integer id) {
        Optional<BusStop> result = Optional.ofNullable(busStopService.findById(id));
        return result.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para obtener todas las paradas de autobús
    @GetMapping("")
    public ResponseEntity<Iterable<BusStop>> findAll() {
        Iterable<BusStop> allBusStops = busStopService.findAll();
        return ResponseEntity.ok(allBusStops);
    }

    // Endpoint para actualizar una parada de autobús existente
    @PutMapping("/{id}")
    public ResponseEntity<BusStop> update(@RequestBody @Valid BusStopUpdateDTO busStopDTO, @PathVariable @NotNull Integer id) {
        BusStop updatedBusStop = busStopService.update(busStopDTO, id);
        return ResponseEntity.ok(updatedBusStop);
    }

    // Endpoint para eliminar una parada de autobús por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable @NotNull Integer id) {
        busStopService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
