package com.labotec.traccar.app.usecase.ports.input.repository;

import com.labotec.traccar.app.utils.common.repository.GenericRepository;
import com.labotec.traccar.domain.database.models.Schedule;
import com.labotec.traccar.domain.database.models.Vehicle;
import com.labotec.traccar.domain.enums.STATE;

import java.time.Instant;
import java.util.List;

/**
 * Interfaz de repositorio para interactuar con las entidades de Schedule (Programación).
 * Esta interfaz proporciona métodos para realizar operaciones CRUD y consultas personalizadas relacionadas con los horarios.
 */
public interface ScheduleRepository extends GenericRepository<Schedule , Long> {

    /**
     * Actualiza el estado de una programación en base a su ID y el ID del usuario.
     *
     * @param resourceId el ID de la programación a actualizar
     * @param status el nuevo estado a asignar
     * @param userId el ID del usuario para asegurar que la programación pertenece al usuario
     */
    void updateStatus(Long resourceId, STATE status, Long userId);

    /**
     * Encuentra todas las programaciones dentro de un rango de fechas específico para un usuario determinado.
     *
     * @param from el inicio del rango de fechas
     * @param to el fin del rango de fechas
     * @param userId el ID del usuario para filtrar las programaciones
     * @return una lista de programaciones dentro del rango de fechas especificado
     */
    List<Schedule> findAllByDateRange(Instant from, Instant to, Long userId);

    /**
     * Actualiza la hora estimada de salida de una programación en base a su ID y el ID del usuario.
     *
     * @param resourceId el ID de la programación a actualizar
     * @param estimatedDepartureTime la nueva hora estimada de salida
     * @param userId el ID del usuario para asegurar que la programación pertenece al usuario
     */
    void updateEstimatedDepartureTime(Long resourceId, Instant estimatedDepartureTime, Long userId);

    /**
     * Actualiza la hora estimada de llegada de una programación en base a su ID y el ID del usuario.
     *
     * @param resourceId el ID de la programación a actualizar
     * @param estimatedArrivalTime la nueva hora estimada de llegada
     * @param userId el ID del usuario para asegurar que la programación pertenece al usuario
     */
    void updateEstimatedArrivalTime(Long resourceId, Instant estimatedArrivalTime, Long userId);

    /**
     * Encuentra todas las programaciones asociadas a un vehículo específico y un usuario determinado.
     *
     * @param vehicleId el ID del vehículo para filtrar las programaciones
     * @param userId el ID del usuario para filtrar las programaciones
     * @return una lista de programaciones asociadas con el vehículo y el usuario dados
     */
    List<Schedule> findByVehicle(Long vehicleId, Long userId);

    /**
     * Encuentra la programación más reciente para un vehículo y usuario específicos.
     * Esta función es útil para obtener el último registro de programación para un vehículo dado.
     *
     * @param vehicleId el ID del vehículo para encontrar la programación más reciente
     * @param userId el ID del usuario para asegurar que la programación pertenece al usuario
     * @return la programación más reciente para el vehículo y usuario dados
     */
    Schedule findByVehicleLastedRegister(Long vehicleId, Long userId);
}
