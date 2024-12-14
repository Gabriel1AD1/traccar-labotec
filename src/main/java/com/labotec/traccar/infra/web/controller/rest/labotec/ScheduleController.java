package com.labotec.traccar.infra.web.controller.rest.labotec;

import com.labotec.traccar.app.ports.out.services.ScheduleService;
import com.labotec.traccar.domain.database.models.Schedule;
import com.labotec.traccar.domain.enums.STATE;
import com.labotec.traccar.domain.web.dto.labotec.request.ReportDelayDTO;
import com.labotec.traccar.domain.web.dto.labotec.request.create.ScheduleDTO;
import com.labotec.traccar.domain.web.dto.labotec.request.update.UpdateScheduleDTO;
import com.labotec.traccar.domain.web.dto.labotec.response.ResponseSuggestTimeSchedule;
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
    public ResponseEntity<Schedule> create(
            @RequestBody @Valid ScheduleDTO scheduleDTO,
            @RequestHeader(name = "userId") Long userId
    ) {
        Schedule createdSchedule = scheduleService.create(scheduleDTO,userId);
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
    public ResponseEntity<Schedule> findById(
            @PathVariable("id") @NotNull Long resourceId,
            @RequestHeader(name = "userId") Long userId
    ) {
        Optional<Schedule> result = Optional.ofNullable(scheduleService.findById(resourceId,userId));
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
    public ResponseEntity<Iterable<Schedule>> findAll(@RequestHeader(name = "userId") Long userId) {
        Iterable<Schedule> allSchedules = scheduleService.findAll(userId);
        return ResponseEntity.ok(allSchedules);
    }

    @PutMapping("/delay")
    public ResponseEntity<Void> updateScheduleForDelay(@Valid @RequestBody ReportDelayDTO reportDelayDTO){
        scheduleService.updateScheduleForDelay(reportDelayDTO);
        return ResponseEntity.noContent().build();
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
    public ResponseEntity<Schedule> update(
            @RequestBody @Valid UpdateScheduleDTO scheduleDTO,
            @PathVariable("id") @NotNull Long resourceId,
            @RequestHeader(name = "userId") Long userId
    ) {
        Schedule updatedSchedule = scheduleService.update(scheduleDTO, resourceId,userId);
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
    public ResponseEntity<Void> deleteById(@PathVariable("id") @NotNull Long resourceId,
                                           @RequestHeader(name = "userId") Long userId) {
        scheduleService.deleteById(resourceId,userId);
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
            @PathVariable("id") Long resourceId,
            @PathVariable("status") STATE status,
            @RequestHeader(name = "userId") Long userId
    ) {
        scheduleService.updateStatus(resourceId, status,userId);
        return ResponseEntity.noContent().build();
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
            @PathVariable("to") Instant to,
            @RequestHeader(name = "userId") Long userId
    ) {
        List<Schedule> schedules = scheduleService.findAllByDateRange(from, to,userId);
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
            @PathVariable("id") Long resourceId,
            @RequestHeader(name = "userId") Long userId,
            @RequestParam("estimatedDepartureTime") @NotEmpty Instant estimatedDepartureTime) {
        scheduleService.updateEstimatedDepartureTime(resourceId, userId,estimatedDepartureTime);
        return ResponseEntity.noContent().build();
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
            @PathVariable("id") Long resourceId,
            @RequestHeader(name = "userId") Long userId,
            @RequestParam("estimated-arrival") @NotEmpty Instant estimatedDepartureTime) {
        scheduleService.updateEstimatedArrivalTime(resourceId, userId,estimatedDepartureTime);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/route/suggest-time/{id}")
    public ResponseEntity<ResponseSuggestTimeSchedule> suggestTimeSchedule(@PathVariable("id")Long routeId, @RequestHeader(name = "userId") Long userId) {return ResponseEntity.ok(scheduleService.suggestMinAndMaxTimeForSchedule(routeId, userId));}
}
