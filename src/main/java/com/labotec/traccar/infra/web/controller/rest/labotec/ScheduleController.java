package com.labotec.traccar.infra.web.controller.rest.labotec;

import com.labotec.traccar.app.usecase.ports.out.ScheduleService;
import com.labotec.traccar.domain.database.models.Schedule;
import com.labotec.traccar.domain.web.dto.ScheduleDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static com.labotec.traccar.infra.web.controller.common.API_VERSION_MANAGER.API_VERSION_V1;

@RestController
@RequestMapping(value = API_VERSION_V1 + "schedule")
@AllArgsConstructor
public class ScheduleController {

    // Inyección del servicio específico de Schedule
    private final ScheduleService scheduleService;

    // Endpoint para crear un nuevo horario
    @PostMapping("")
    public ResponseEntity<Schedule> create(@RequestBody @Valid ScheduleDTO scheduleDTO) {
        Schedule createdSchedule = scheduleService.create(scheduleDTO);
        return ResponseEntity.ok(createdSchedule);
    }

    // Endpoint para obtener un horario por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Schedule> findById(@PathVariable @NotNull Integer id) {
        Optional<Schedule> result = Optional.ofNullable(scheduleService.findById(id));
        return result.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para obtener todos los horarios
    @GetMapping("")
    public ResponseEntity<Iterable<Schedule>> findAll() {
        Iterable<Schedule> allSchedules = scheduleService.findAll();
        return ResponseEntity.ok(allSchedules);
    }

    // Endpoint para actualizar un horario existente
    @PutMapping("/{id}")
    public ResponseEntity<Schedule> update(@RequestBody @Valid ScheduleDTO scheduleDTO, @PathVariable @NotNull Integer id) {
        Schedule updatedSchedule = scheduleService.update(scheduleDTO, id);
        return ResponseEntity.ok(updatedSchedule);
    }

    // Endpoint para eliminar un horario por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable @NotNull Integer id) {
        scheduleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/status/{status}")
    public ResponseEntity<Schedule> updateStatus(
            @PathVariable Integer id,
            @PathVariable Byte status
    ) {
        Schedule updatedSchedule = scheduleService.updateStatus(id, status);
        return ResponseEntity.ok(updatedSchedule);
    }

    @GetMapping("/from/{from}/to/{to}")
    public ResponseEntity<List<Schedule>> getSchedulesByDateRange(
            @PathVariable("from") Instant from,
            @PathVariable("to") Instant to) {

        // Llamada al servicio para filtrar las programaciones
        List<Schedule> schedules = scheduleService.findAllByDateRange(from, to);

        return ResponseEntity.ok(schedules);
    }


    @PutMapping("/{id}/estimated-departure")
    public ResponseEntity<Schedule> updateEstimatedDepartureTime(
            @PathVariable Integer id,
            @RequestParam("estimatedDepartureTime") @NotEmpty Instant estimatedDepartureTime) {
        Schedule updatedSchedule = scheduleService.updateEstimatedDepartureTime(id, estimatedDepartureTime);
        return ResponseEntity.ok(updatedSchedule);
    }

    @PutMapping("/{id}/estimated-arrival")
    public ResponseEntity<Schedule> updateEstimatedArrivalTime(
            @PathVariable Integer id,
            @RequestParam("estimated-arrival") @NotEmpty Instant estimatedDepartureTime) {
        Schedule updatedSchedule = scheduleService.updateEstimatedArrivalTime(id, estimatedDepartureTime);
        return ResponseEntity.ok(updatedSchedule);
    }
}
