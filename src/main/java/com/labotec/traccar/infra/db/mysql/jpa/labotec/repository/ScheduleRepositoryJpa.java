package com.labotec.traccar.infra.db.mysql.jpa.labotec.repository;

import com.labotec.traccar.domain.database.models.Vehicle;
import com.labotec.traccar.domain.enums.STATE;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.ScheduleEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.VehicleEntity;
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
public interface ScheduleRepositoryJpa extends JpaRepository<ScheduleEntity, Integer> {
    // Consulta para filtrar las programaciones por un rango de fechas
    @Query("SELECT s FROM ScheduleEntity s WHERE s.userId.userId = :userId AND s.vehicle.id = :vehicleId")
    List<ScheduleEntity> findByUserIdAndVehicleId(@Param("userId") Long userId, @Param("vehicleId") Long vehicleId);

    @Query("SELECT s FROM ScheduleEntity s WHERE s.departureTime BETWEEN :from AND :to AND s.userId.userId = :userId")
    List<ScheduleEntity> findAllByDepartureTimeBetweenAndUserId(@Param("from") Instant from, @Param("to") Instant to, @Param("userId") Long userId);
    @Query("SELECT s FROM ScheduleEntity s WHERE s.vehicle = :vehicle AND s.userId.userId = :userId ORDER BY s.departureTime DESC")
    Optional<ScheduleEntity> findTopByVehicleAndUserOrderByDepartureTimeDesc(@Param("vehicle") VehicleEntity vehicle, @Param("userId") Long userId);
    @Modifying
    @Transactional
    @Query("UPDATE ScheduleEntity s SET s.status = :status WHERE s.id = :id AND s.userId.userId = :userId")
    void updateStatusByUserIdAndId(@Param("status") STATE status, @Param("id") Long id, @Param("userId") Long userId);
    List<ScheduleEntity> findAllByUserId_UserId(Long userId);
    Optional<ScheduleEntity> findByIdAndUserId_UserId(Long resourceId,Long userId);
    void deleteByIdAndUserId_UserId(Long resourceId,Long userId);
    @Modifying
    @Transactional
    @Query("UPDATE ScheduleEntity s SET s.estimatedDepartureTime = :estimatedDepartureTime WHERE s.id = :resourceId AND s.userId.id = :userId")
    void updateEstimatedDepartureTime(@Param("resourceId") Long resourceId, @Param("estimatedDepartureTime") Instant estimatedDepartureTime, @Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE ScheduleEntity s SET s.estimatedArrivalTime = :estimatedArrivalTime WHERE s.id = :resourceId AND s.userId.userId = :userId")
    void updateEstimatedArrivalTime(@Param("resourceId") Long resourceId, @Param("estimatedArrivalTime") Instant estimatedArrivalTime, @Param("userId") Long userId);
    @Query("SELECT s FROM ScheduleEntity s WHERE s.vehicle.id = :vehicleId AND s.userId.id = :userId ORDER BY s.departureTime DESC")
    Optional<ScheduleEntity> findTopByVehicleAndUserOrderByDepartureTimeDesc(@Param("vehicleId") Long vehicleId, @Param("userId") Long userId);
}
