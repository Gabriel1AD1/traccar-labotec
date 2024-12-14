package com.labotec.traccar.infra.db.mysql.jpa.labotec.repository;

import com.labotec.traccar.app.enums.RouteType;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.RouteEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.projection.RouteResponseProjection;
import org.springframework.data.jpa.repository.EntityGraph;
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
public interface RouteRepositoryJpa extends JpaRepository<RouteEntity, Long> {

    /**
     * Busca una ruta específica por su ID y el ID del usuario.
     *
     * @param userId El ID del usuario asociado a la ruta.
     * @param routeId El ID de la ruta que se busca.
     * @return Un {@link Optional<RouteEntity>} que contiene la ruta si existe, o {@link Optional#empty()} si no se encuentra.
     */
    @Query("SELECT r FROM RouteEntity r WHERE r.userId.userId = :userId AND r.id = :routeId")
    Optional<RouteEntity> findRouteByUserIdAndRouteId(@Param("userId") Long userId, @Param("routeId") Long routeId);

    /**
     * Busca la ruta asociada a un vehículo y verifica si está activa en el tiempo actual.
     *
     * @param vehicleId El ID del vehículo cuya ruta se desea encontrar.
     * @param currentTime El tiempo actual utilizado para verificar si la ruta está activa.
     * @return Un {@link Optional<RouteEntity>} que contiene la ruta si está activa en el tiempo proporcionado, o {@link Optional#empty()} si no.
     */
    @Query("SELECT s.route FROM ScheduleEntity s " +
            "WHERE s.vehicle.deviceId = :vehicleId " +
            "AND :currentTime BETWEEN s.estimatedDepartureTime AND s.estimatedArrivalTime")
    Optional<RouteEntity> findRouteByVehicleAndCurrentTime(
            @Param("vehicleId") Long vehicleId,
            @Param("currentTime") Instant currentTime);

    /**
     * Obtiene el tipo de ruta (RouteType) de una ruta específica por su ID.
     *
     * @param routeId El ID de la ruta para la cual se desea obtener el tipo.
     * @return Un {@link Optional<RouteType>} que contiene el tipo de ruta si se encuentra, o {@link Optional#empty()} si no.
     */
    @Query("SELECT r.routeType FROM RouteEntity r WHERE r.id = :routeId")
    Optional<RouteType> findRouteTypeByRouteId(@Param("routeId") Long routeId);

    /**
     * Busca todas las rutas asociadas a un usuario por su ID, devolviendo solo ciertos campos específicos de las rutas.
     *
     * @param userId El ID del usuario cuyas rutas se desean obtener.
     * @return Una lista de {@link RouteResponseProjection} que contiene solo los campos necesarios (id, name, distanceMaxInKM, distanceMinInKM, routeType).
     */
    @Query("SELECT r.id as id, r.name as name, r.distanceMaxInKM as distanceMaxInKM, " +
            "r.distanceMinInKM as distanceMinInKM, r.routeType as routeType " +
            "FROM RouteEntity r WHERE r.userId.userId = :userId")
    List<RouteResponseProjection> findRoutesByUserId(@Param("userId") Long userId);


    boolean existsByIdAndUserId_UserId(Long routeId, Long userId);

    /**
     * Elimina una ruta por su ID y el ID del usuario.
     *
     * @param resourceId El ID de la ruta a eliminar.
     * @param userId El ID del usuario asociado.
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM RouteEntity r WHERE r.id = :resourceId AND r.userId.userId = :userId")
    int deleteByIdAndUserId(@Param("resourceId") Long resourceId, @Param("userId") Long userId);

    @EntityGraph(attributePaths = {"userId"})
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END " +
            "FROM RouteEntity r " +
            "WHERE r.id = :routeId AND r.userId.userId = :userId")
    Boolean existsByRouteIdAndUserId(@Param("routeId") Long routeId, @Param("userId") Long userId);
}
