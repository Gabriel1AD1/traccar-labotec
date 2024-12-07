package com.labotec.traccar.app.ports.out;

import com.labotec.traccar.app.utils.common.repository.GenericCrudService;
import com.labotec.traccar.domain.database.models.Schedule;
import com.labotec.traccar.domain.enums.STATE;
import com.labotec.traccar.domain.web.dto.labotec.request.ReportDelayDTO;
import com.labotec.traccar.domain.web.dto.labotec.request.create.ScheduleDTO;
import com.labotec.traccar.domain.web.dto.labotec.request.update.UpdateScheduleDTO;

import java.time.Instant;
import java.util.List;

/**
 * Interfaz del servicio para gestionar las operaciones de programación (Schedule).
 * Esta interfaz proporciona métodos para realizar operaciones de actualización y consulta sobre las programaciones.
 *
 */
public interface ScheduleService extends GenericCrudService<Schedule , ScheduleDTO , UpdateScheduleDTO, Long> {

    /**
     * Actualiza el estado de una programación en base a su ID y el ID del usuario.
     *
     * @param id el ID de la programación a actualizar
     * @param status el nuevo estado a asignar
     * @param userId el ID del usuario para asegurar que la programación pertenece al usuario
     */
    void updateStatus(Long id, STATE status, Long userId);

    /**
     * Encuentra todas las programaciones dentro de un rango de fechas específico para un usuario.
     *
     * @param from la fecha de inicio del rango
     * @param to la fecha de fin del rango
     * @param userId el ID del usuario para filtrar las programaciones
     * @return una lista de programaciones dentro del rango de fechas especificado
     */
    List<Schedule> findAllByDateRange(Instant from, Instant to, Long userId);

    /**
     * Actualiza la hora estimada de salida de una programación en base a su ID, el ID del usuario y la nueva hora estimada de salida.
     *
     * @param resourceId el ID de la programación a actualizar
     * @param userId el ID del usuario para asegurar que la programación pertenece al usuario
     * @param estimatedDepartureTime la nueva hora estimada de salida
     */
    void updateEstimatedDepartureTime(Long resourceId, Long userId, Instant estimatedDepartureTime);

    /**
     * Actualiza la hora estimada de llegada de una programación en base a su ID, el ID del usuario y la nueva hora estimada de llegada.
     *
     * @param id el ID de la programación a actualizar
     * @param userId el ID del usuario para asegurar que la programación pertenece al usuario
     * @param estimatedArrivalTime la nueva hora estimada de llegada
     */
    void updateEstimatedArrivalTime(Long id, Long userId, Instant estimatedArrivalTime);

    /**
     * Encuentra las programaciones asociadas a un vehículo específico y un usuario determinado.
     *
     * @param vehicle el ID del vehículo para filtrar las programaciones
     * @param userId el ID del usuario para filtrar las programaciones
     * @return una lista de programaciones asociadas con el vehículo y el usuario dados
     */
    List<Schedule> findScheduleByVehicle(Long vehicle, Long userId);


    void updateScheduleForDelay(ReportDelayDTO reportDelayDTO);
}
