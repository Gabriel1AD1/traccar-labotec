package com.labotec.traccar.infra.db.mysql.jpa.labotec.repository;

import com.labotec.traccar.domain.database.models.Vehicle;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.ScheduleEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepositoryJpa extends JpaRepository<ScheduleEntity, Integer> {
    // Consulta para filtrar las programaciones por un rango de fechas
    @Query("SELECT s FROM ScheduleEntity s WHERE s.departureTime BETWEEN :from AND :to")
    List<ScheduleEntity> findAllByDepartureTimeBetween(@Param("from") Instant from, @Param("to") Instant to);

    List<ScheduleEntity> findByVehicle(VehicleEntity vehicleId);

    @Query("SELECT s FROM ScheduleEntity s WHERE s.vehicle = :vehicle ORDER BY s.departureTime DESC")
    Optional<ScheduleEntity> findTopByVehicleOrderByDepartureTimeDesc(@Param("vehicle") VehicleEntity vehicle);

}
