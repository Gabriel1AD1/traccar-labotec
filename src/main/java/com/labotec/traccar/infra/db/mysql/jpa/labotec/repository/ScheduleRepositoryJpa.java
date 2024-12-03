package com.labotec.traccar.infra.db.mysql.jpa.labotec.repository;

import com.labotec.traccar.domain.database.models.Vehicle;
import com.labotec.traccar.domain.enums.STATE;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.ScheduleEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.VehicleEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.projection.InformationScheduleProjection;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.projection.ScheduleProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepositoryJpa extends JpaRepository<ScheduleEntity, Long> {
    /**
     * Obtiene todas las programaciones asociadas a un usuario y un vehículo específicos.
     *
     * @param userId el ID del usuario.
     * @param vehicleId el ID del vehículo.
     * @return una lista de programaciones asociadas al usuario y vehículo.
     */
    @Query("SELECT s FROM ScheduleEntity s WHERE s.userId.userId = :userId AND s.vehicle.id = :vehicleId")
    List<ScheduleEntity> findByUserIdAndVehicleId(@Param("userId") Long userId, @Param("vehicleId") Long vehicleId);

    /**
     * Obtiene todas las programaciones dentro de un rango de tiempo para un usuario específico.
     *
     * @param from el tiempo de inicio del rango.
     * @param to el tiempo de fin del rango.
     * @param userId el ID del usuario.
     * @return una lista de programaciones dentro del rango de tiempo.
     */
    @Query("SELECT s FROM ScheduleEntity s WHERE s.departureTime BETWEEN :from AND :to AND s.userId.userId = :userId")
    List<ScheduleEntity> findAllByDepartureTimeBetweenAndUserId(@Param("from") Instant from, @Param("to") Instant to, @Param("userId") Long userId);

    /**
     * Encuentra la última programación asignada a un vehículo para un usuario específico.
     *
     * @param vehicle el vehículo asociado.
     * @param userId el ID del usuario.
     * @return la programación más reciente para el vehículo y usuario, si existe.
     */
    @Query("SELECT s FROM ScheduleEntity s WHERE s.vehicle = :vehicle AND s.userId.userId = :userId ORDER BY s.departureTime DESC")
    Optional<ScheduleEntity> findTopByVehicleAndUserOrderByDepartureTimeDesc(@Param("vehicle") VehicleEntity vehicle, @Param("userId") Long userId);

    /**
     * Actualiza el estado de una programación específica para un usuario.
     *
     * @param status el nuevo estado.
     * @param id el ID de la programación.
     * @param userId el ID del usuario asociado.
     */
    @Modifying
    @Transactional
    @Query("UPDATE ScheduleEntity s SET s.status = :status WHERE s.id = :id AND s.userId.userId = :userId")
    void updateStatusByUserIdAndId(@Param("status") STATE status, @Param("id") Long id, @Param("userId") Long userId);

    /**
     * Obtiene todas las programaciones asociadas a un usuario específico.
     *
     * @param userId el ID del usuario.
     * @return una lista de programaciones del usuario.
     */
    List<ScheduleEntity> findAllByUserId_UserId(Long userId);

    /**
     * Encuentra una programación específica asociada a un usuario.
     *
     * @param resourceId el ID de la programación.
     * @param userId el ID del usuario.
     * @return la programación encontrada, si existe.
     */
    Optional<ScheduleEntity> findByIdAndUserId_UserId(Long resourceId, Long userId);

    /**
     * Elimina una programación específica asociada a un usuario.
     *
     * @param resourceId el ID de la programación.
     * @param userId el ID del usuario.
     */
    void deleteByIdAndUserId_UserId(Long resourceId, Long userId);

    /**
     * Actualiza el tiempo estimado de salida para una programación específica.
     *
     * @param resourceId el ID de la programación.
     * @param estimatedDepartureTime el nuevo tiempo estimado de salida.
     * @param userId el ID del usuario asociado.
     */
    @Modifying
    @Transactional
    @Query("UPDATE ScheduleEntity s SET s.estimatedDepartureTime = :estimatedDepartureTime WHERE s.id = :resourceId AND s.userId.userId = :userId")
    void updateEstimatedDepartureTime(@Param("resourceId") Long resourceId, @Param("estimatedDepartureTime") Instant estimatedDepartureTime, @Param("userId") Long userId);

    /**
     * Actualiza el tiempo estimado de llegada para una programación específica.
     *
     * @param resourceId el ID de la programación.
     * @param estimatedArrivalTime el nuevo tiempo estimado de llegada.
     * @param userId el ID del usuario asociado.
     */
    @Modifying
    @Transactional
    @Query("UPDATE ScheduleEntity s SET s.estimatedArrivalTime = :estimatedArrivalTime WHERE s.id = :resourceId AND s.userId.userId = :userId")
    void updateEstimatedArrivalTime(@Param("resourceId") Long resourceId, @Param("estimatedArrivalTime") Instant estimatedArrivalTime, @Param("userId") Long userId);

    /**
     * Encuentra la última programación asignada a un vehículo, ordenada por tiempo de salida, para un usuario específico.
     *
     * @param vehicleId el ID del vehículo.
     * @param userId el ID del usuario.
     * @return la programación más reciente para el vehículo y usuario, si existe.
     */
    @Query("SELECT s FROM ScheduleEntity s WHERE s.vehicle.id = :vehicleId AND s.userId.userId = :userId ORDER BY s.departureTime DESC")
    Optional<ScheduleEntity> findTopByVehicleAndUserOrderByDepartureTimeDesc(@Param("vehicleId") Long vehicleId, @Param("userId") Long userId);

    /**
     * Verifica si existe un horario existente que se solape con el rango de tiempo especificado para un vehículo dado.
     *
     * <p>Este función utiliza una consulta para determinar si hay horarios asignados al vehículo especificado
     * que se solapen con los tiempos propuestos de salida y llegada. Un solapamiento ocurre si:
     * <ul>
     *   <li>Un horario existente comienza antes del tiempo de llegada propuesto.</li>
     *   <li>Un horario existente termina después del tiempo de salida propuesto.</li>
     * </ul>
     *
     * @param vehicleId el ID del vehículo para el cual se quiere verificar solapamientos de horarios.
     * @param newDepartureTime el tiempo de salida propuesto para el nuevo horario.
     * @param newArrivalTime el tiempo de llegada propuesto para el nuevo horario.
     * @return {@code true} si existe al menos un horario que se solape; {@code false} en caso contrario.
     */
    @Query("SELECT COUNT(s) > 0 " +
            "FROM ScheduleEntity s " +
            "WHERE s.vehicle.traccarDeviceId = :vehicleId " +
            "AND s.estimatedDepartureTime < :newArrivalTime " +
            "AND s.estimatedArrivalTime > :newDepartureTime")
    boolean existsOverlappingSchedule(
            @Param("vehicleId") Long vehicleId,
            @Param("newDepartureTime") Instant newDepartureTime,
            @Param("newArrivalTime") Instant newArrivalTime
    );

    @Query("SELECT s FROM ScheduleEntity s " +
            "WHERE s.vehicle.traccarDeviceId = :vehicleId " +
            "AND :currentTime BETWEEN s.estimatedDepartureTime AND s.estimatedArrivalTime " +
            "ORDER BY s.estimatedDepartureTime ASC")
    Optional<ScheduleEntity> findScheduleByVehicleAndCurrentTime(
            @Param("vehicleId") Long vehicleId,
            @Param("currentTime") Instant currentTime
    );
    @Query("SELECT s.id as id," +
            "s.route AS route, " +
            "s.geofenceType AS geofenceType, " +
            "s.geofenceId AS geofenceId, " +
            "s.radiusValidateRoutePolyline AS radiusValidateRoutePolyline, " +
            "s.validateRouteExplicit AS validateRouteExplicit, " +
            "s.status AS status " +
            "FROM ScheduleEntity s " +
            "WHERE s.vehicle.traccarDeviceId = :vehicleId " +
            "AND :currentTime BETWEEN s.estimatedDepartureTime AND s.estimatedArrivalTime " +
            "ORDER BY s.estimatedDepartureTime ASC")
    Optional<ScheduleProjection> findScheduleProjectionViewByVehicleAndCurrentTime(
            @Param("vehicleId") Long vehicleId,
            @Param("currentTime") Instant currentTime
    );
    @Query("SELECT s.route.id " +
            "FROM ScheduleEntity s " +
            "WHERE s.vehicle.traccarDeviceId = :vehicleId " +
            "AND :currentTime BETWEEN s.estimatedDepartureTime AND s.estimatedArrivalTime " +
            "ORDER BY s.estimatedDepartureTime ASC")
    Optional<Long> findRouteIdByScheduleForVehicleIdAndCurrentTime(
            @Param("vehicleId") Long vehicleId,
            @Param("currentTime") Instant currentTime
    );

    @Query("SELECT s.route.id AS routeId, s.id AS scheduleId " +
            "FROM ScheduleEntity s " +
            "WHERE s.vehicle.traccarDeviceId = :vehicleId " +
            "AND :currentTime BETWEEN s.estimatedDepartureTime AND s.estimatedArrivalTime " +
            "ORDER BY s.estimatedDepartureTime ASC")
    Optional<InformationScheduleProjection> findByInformationScheduleIds(
            @Param("vehicleId") Long vehicleId,
            @Param("currentTime") Instant currentTime
    );



    // Actualizar departureTime por ID
    @Transactional
    @Modifying
    @Query("UPDATE ScheduleEntity s SET s.departureTime = :departureTime WHERE s.id = :id")
    void updateDepartureTimeById(Long id, Instant departureTime);

    // Actualizar arrivalTime por ID
    @Transactional
    @Modifying
    @Query("UPDATE ScheduleEntity s SET s.arrivalTime = :arrivalTime WHERE s.id = :id")
    void updateArrivalTimeById(Long id, Instant arrivalTime);
}
