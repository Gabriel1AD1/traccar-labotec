package com.labotec.traccar.infra.db.mysql.jpa.labotec.repository;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.DriverEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.DriverScheduleEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DriverScheduleRepositoryJpa extends JpaRepository<DriverScheduleEntity, Long> {

    // Verificar si un conductor ya está asignado a una programación
    @Query("SELECT COUNT(ds) > 0 " +
            "FROM DriverScheduleEntity ds " +
            "WHERE ds.driverId.id = :driverId AND ds.scheduleId.id = :scheduleId")
    boolean existsByDriverAndSchedule(@Param("driverId") Long driverId, @Param("scheduleId") Long scheduleId);

    // Obtener todas las programaciones asignadas a un conductor
    @Query("SELECT ds.scheduleId FROM DriverScheduleEntity ds WHERE ds.driverId.id = :driverId")
    List<ScheduleEntity> findSchedulesByDriverId(@Param("driverId") Long driverId);

    // Obtener todos los conductores asignados a una programación
    @Query("SELECT ds.driverId FROM DriverScheduleEntity ds WHERE ds.scheduleId.id = :scheduleId")
    List<DriverEntity> findDriversByScheduleId(@Param("scheduleId") Long scheduleId);

    @Modifying
    @Transactional
    @Query("DELETE FROM DriverScheduleEntity ds WHERE ds.driverId.id = :driverId AND ds.scheduleId.id = :scheduleId")
    void deleteByDriverAndSchedule(@Param("driverId") Long driverId, @Param("scheduleId") Long scheduleId);
}
