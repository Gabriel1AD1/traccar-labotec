package com.labotec.traccar.infra.db.mysql.jpa.labotec.repository;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface ScheduleRepositoryJpa extends JpaRepository<ScheduleEntity, Integer> {
    // Consulta para filtrar las programaciones por un rango de fechas
    @Query("SELECT s FROM ScheduleEntity s WHERE s.departureTime BETWEEN :from AND :to")
    List<ScheduleEntity> findAllByDepartureTimeBetween(@Param("from") Instant from, @Param("to") Instant to);

    List<ScheduleEntity> findByVehicleId(Integer vehicleId);
}
