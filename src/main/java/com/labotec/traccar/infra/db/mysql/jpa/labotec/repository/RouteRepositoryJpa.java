package com.labotec.traccar.infra.db.mysql.jpa.labotec.repository;

import com.labotec.traccar.app.enums.RouteType;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.RouteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface RouteRepositoryJpa extends JpaRepository<RouteEntity, Long> {
    @Query("SELECT r FROM RouteEntity r WHERE r.userId.userId = :userId AND r.id = :routeId")
    Optional<RouteEntity> findRouteByUserIdAndRouteId(@Param("userId") Long userId, @Param("routeId") Long routeId);

    @Query("SELECT s.route FROM ScheduleEntity s " +
            "WHERE s.vehicle.traccarDeviceId = :vehicleId " +
            "AND :currentTime BETWEEN s.estimatedDepartureTime AND s.estimatedArrivalTime")
    Optional<RouteEntity> findRouteByVehicleAndCurrentTime(
            @Param("vehicleId") Long vehicleId,
            @Param("currentTime") Instant currentTime);

    @Query("SELECT r.routeType FROM RouteEntity r WHERE r.id = :routeId")
    Optional<RouteType> findRouteTypeByRouteId(@Param("routeId") Long routeId);
}
