package com.labotec.traccar.app.ports.input.repository;

import com.labotec.traccar.domain.database.models.StopRegister;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.List;

public interface StopRegisterRepository {

    StopRegister create(StopRegister stopRegister);
    List<StopRegister> findByScheduleId(Long scheduleId);
    void updateEntryTimeForRegisterBuStop(Long scheduleID, Long busStopId,Instant now);
    void updateExitTimeForRegisterBusStop(Long scheduleId,Long busStopId,Instant now);
    void updateAlertSend(Long scheduleId, @NotNull Long busStopId, @NotNull Boolean alertSend);
    void updateTimeExceeded(Long scheduleId, @NotNull Long busStopId, @NotNull Boolean timeExceeded);
    void updateMinimumTimeMet(Long scheduleId, @NotNull Long busStopId, @NotNull Boolean timeSpent);
    // Actualizar el exceso de tiempo máximo (maxTimeExcess) para un scheduleId y busStopId específicos
    void updateMaxTimeExcess(Long scheduleId, Long busStopId, Integer maxTimeExcess);

    // Actualizar el exceso de tiempo mínimo (minTimeShortfall) para un scheduleId y busStopId específicos
    void updateMinTimeShortfall(Long scheduleId, Long busStopId, Integer minTimeShortfall);
}
