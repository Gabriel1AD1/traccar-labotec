package com.labotec.traccar.infra.db.mysql.jpa.labotec.repository;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.StopRegisterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

public interface StopRegisterRepositoryJpa extends JpaRepository<StopRegisterEntity, Long> {
    List<StopRegisterEntity> findByScheduleId(Long scheduleId);
    @Transactional
    @Modifying
    @Query("UPDATE StopRegisterEntity s SET s.entryTime = :now WHERE s.scheduleId = :scheduleId AND s.busStopId = :busStopId AND s.entryTime IS NULL")
    int updateEntryTimeIfNull(@Param("scheduleId") Long scheduleId,
                              @Param("busStopId") Long busStopId,
                              @Param("now") Instant now);

    @Transactional
    @Modifying
    @Query("UPDATE StopRegisterEntity s SET s.exitTime = :now WHERE s.scheduleId = :scheduleId AND s.busStopId = :busStopId AND s.exitTime IS NULL")
    int updateExitTimeIfNull(@Param("scheduleId") Long scheduleId,
                             @Param("busStopId") Long busStopId,
                             @Param("now") Instant now);

    @Transactional
    @Modifying
    @Query("UPDATE StopRegisterEntity s " +
            "SET s.alerted = CASE WHEN :alerted = false THEN s.alerted ELSE :alerted END " +
            "WHERE s.scheduleId = :scheduleId AND s.busStopId = :busStopId")
    int updateAlerted(@Param("scheduleId") Long scheduleId,
                      @Param("busStopId") Long busStopId,
                      @Param("alerted") Boolean alerted);


    @Transactional
    @Modifying
    @Query("UPDATE StopRegisterEntity s " +
            "SET s.timeExceeded = CASE WHEN :timeExceeded = false THEN s.timeExceeded ELSE :timeExceeded END " +
            "WHERE s.scheduleId = :scheduleId AND s.busStopId = :busStopId")
    int updateTimeExceeded(@Param("scheduleId") Long scheduleId,
                           @Param("busStopId") Long busStopId,
                           @Param("timeExceeded") Boolean timeExceeded);
    @Modifying
    @Transactional
    @Query("UPDATE StopRegisterEntity s SET s.isMinimumTimeMet = :isMinimumTimeMet WHERE s.scheduleId = :scheduleId AND s.busStopId = :busStopId")
    int updateMinimumTimeMet(@Param("scheduleId") Long scheduleId,
                             @Param("busStopId") Long busStopId,
                             @Param("isMinimumTimeMet") Boolean isMinimumTimeMet);

    // Actualizar el exceso de tiempo máximo (maxTimeExcess) para un scheduleId y busStopId específicos
    @Modifying
    @Transactional
    @Query("UPDATE StopRegisterEntity s " +
            "SET s.maxTimeExcess = :maxTimeExcess " +
            "WHERE s.scheduleId = :scheduleId AND s.busStopId = :busStopId")
    int updateMaxTimeExcess(Long scheduleId, Long busStopId, Integer maxTimeExcess);

    // Actualizar el exceso de tiempo mínimo (minTimeShortfall) para un scheduleId y busStopId específicos
    @Modifying
    @Transactional
    @Query("UPDATE StopRegisterEntity s " +
            "SET s.minTimeShortfall = :minTimeShortfall " +
            "WHERE s.scheduleId = :scheduleId AND s.busStopId = :busStopId")
    int updateMinTimeShortfall(Long scheduleId, Long busStopId, Integer minTimeShortfall);
}