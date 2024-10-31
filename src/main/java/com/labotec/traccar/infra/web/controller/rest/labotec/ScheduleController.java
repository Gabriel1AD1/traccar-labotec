package com.labotec.traccar.infra.web.controller.rest.labotec;

import com.labotec.traccar.app.usecase.ports.out.ScheduleService;
import com.labotec.traccar.domain.database.models.Schedule;
import com.labotec.traccar.domain.web.dto.entel.create.ScheduleDTO;
import com.labotec.traccar.domain.web.dto.entel.update.ScheduleUpdateDTO;
import com.labotec.traccar.infra.web.controller.rest.constant.ApiDocumentationConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    private final ScheduleService scheduleService;

    // Crear una nueva programación
    @Operation(
            summary = ApiDocumentationConstants.CREATE_SCHEDULE_SUMMARY,
            description = ApiDocumentationConstants.CREATE_SCHEDULE_DESCRIPTION,
            responses = {
                    @ApiResponse(responseCode = "200", description = ApiDocumentationConstants.RESPONSE_200)
            }
    )
    @PostMapping("")
    public ResponseEntity<Schedule> create(@RequestBody @Valid ScheduleDTO scheduleDTO) {
        Schedule createdSchedule = scheduleService.create(scheduleDTO);
        return ResponseEntity.ok(createdSchedule);
    }

    // Obtener programación por ID
    @Operation(
            summary = ApiDocumentationConstants.FIND_SCHEDULE_BY_ID_SUMMARY,
            description = ApiDocumentationConstants.FIND_SCHEDULE_BY_ID_DESCRIPTION,
            responses = {
                    @ApiResponse(responseCode = "200", description = ApiDocumentationConstants.RESPONSE_200),
                    @ApiResponse(responseCode = "404", description = ApiDocumentationConstants.RESPONSE_404)
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Schedule> findById(@PathVariable @NotNull Integer id) {
        Optional<Schedule> result = Optional.ofNullable(scheduleService.findById(id));
        return result.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Obtener todas las programaciones
    @Operation(
            summary = ApiDocumentationConstants.FIND_ALL_SCHEDULES_SUMMARY,
            description = ApiDocumentationConstants.FIND_ALL_SCHEDULES_DESCRIPTION,
            responses = {
                    @ApiResponse(responseCode = "200", description = ApiDocumentationConstants.RESPONSE_200)
            }
    )
    @GetMapping("")
    public ResponseEntity<Iterable<Schedule>> findAll() {
        Iterable<Schedule> allSchedules = scheduleService.findAll();
        return ResponseEntity.ok(allSchedules);
    }

    // Actualizar programación existente
    @Operation(
            summary = ApiDocumentationConstants.UPDATE_SCHEDULE_SUMMARY,
            description = ApiDocumentationConstants.UPDATE_SCHEDULE_DESCRIPTION,
            responses = {
                    @ApiResponse(responseCode = "200", description = ApiDocumentationConstants.RESPONSE_200),
                    @ApiResponse(responseCode = "404", description = ApiDocumentationConstants.RESPONSE_404)
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Schedule> update(@RequestBody @Valid ScheduleUpdateDTO scheduleDTO, @PathVariable @NotNull Integer id) {
        Schedule updatedSchedule = scheduleService.update(scheduleDTO, id);
        return ResponseEntity.ok(updatedSchedule);
    }

    // Eliminar programación por ID
    @Operation(
            summary = ApiDocumentationConstants.DELETE_SCHEDULE_SUMMARY,
            description = ApiDocumentationConstants.DELETE_SCHEDULE_DESCRIPTION,
            responses = {
                    @ApiResponse(responseCode = "204", description = "Eliminación exitosa"),
                    @ApiResponse(responseCode = "404", description = ApiDocumentationConstants.RESPONSE_404)
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable @NotNull Integer id) {
        scheduleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Actualizar estado de programación
    @Operation(
            summary = ApiDocumentationConstants.UPDATE_STATUS_SUMMARY,
            description = ApiDocumentationConstants.UPDATE_STATUS_DESCRIPTION,
            responses = {
                    @ApiResponse(responseCode = "200", description = ApiDocumentationConstants.RESPONSE_200)
            }
    )
    @PutMapping("/{id}/status/{status}")
    public ResponseEntity<Schedule> updateStatus(
            @PathVariable Integer id,
            @PathVariable Byte status
    ) {
        Schedule updatedSchedule = scheduleService.updateStatus(id, status);
        return ResponseEntity.ok(updatedSchedule);
    }

    // Obtener programaciones por rango de fechas
    @Operation(
            summary = ApiDocumentationConstants.GET_SCHEDULES_BY_DATE_RANGE_SUMMARY,
            description = ApiDocumentationConstants.GET_SCHEDULES_BY_DATE_RANGE_DESCRIPTION,
            responses = {
                    @ApiResponse(responseCode = "200", description = ApiDocumentationConstants.RESPONSE_200)
            }
    )
    @GetMapping("/from/{from}/to/{to}")
    public ResponseEntity<List<Schedule>> getSchedulesByDateRange(
            @PathVariable("from") Instant from,
            @PathVariable("to") Instant to) {
        List<Schedule> schedules = scheduleService.findAllByDateRange(from, to);
        return ResponseEntity.ok(schedules);
    }

    // Actualizar hora estimada de salida
    @Operation(
            summary = ApiDocumentationConstants.UPDATE_ESTIMATED_DEPARTURE_SUMMARY,
            description = ApiDocumentationConstants.UPDATE_ESTIMATED_DEPARTURE_DESCRIPTION,
            responses = {
                    @ApiResponse(responseCode = "200", description = ApiDocumentationConstants.RESPONSE_200)
            }
    )
    @PutMapping("/{id}/estimated-departure")
    public ResponseEntity<Schedule> updateEstimatedDepartureTime(
            @PathVariable Integer id,
            @RequestParam("estimatedDepartureTime") @NotEmpty Instant estimatedDepartureTime) {
        Schedule updatedSchedule = scheduleService.updateEstimatedDepartureTime(id, estimatedDepartureTime);
        return ResponseEntity.ok(updatedSchedule);
    }

    // Actualizar hora estimada de llegada
    @Operation(
            summary = ApiDocumentationConstants.UPDATE_ESTIMATED_ARRIVAL_SUMMARY,
            description = ApiDocumentationConstants.UPDATE_ESTIMATED_ARRIVAL_DESCRIPTION,
            responses = {
                    @ApiResponse(responseCode = "200", description = ApiDocumentationConstants.RESPONSE_200)
            }
    )
    @PutMapping("/{id}/estimated-arrival")
    public ResponseEntity<Schedule> updateEstimatedArrivalTime(
            @PathVariable Integer id,
            @RequestParam("estimated-arrival") @NotEmpty Instant estimatedDepartureTime) {
        Schedule updatedSchedule = scheduleService.updateEstimatedArrivalTime(id, estimatedDepartureTime);
        return ResponseEntity.ok(updatedSchedule);
    }
}
