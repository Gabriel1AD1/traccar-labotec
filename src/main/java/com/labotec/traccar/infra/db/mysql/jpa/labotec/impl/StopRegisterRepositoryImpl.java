package com.labotec.traccar.infra.db.mysql.jpa.labotec.impl;

import com.labotec.traccar.app.ports.input.repository.StopRegisterRepository;
import com.labotec.traccar.domain.database.models.StopRegister;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.StopRegisterEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.StopRegisterMapper;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.StopRegisterRepositoryJpa;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
@AllArgsConstructor
public class StopRegisterRepositoryImpl implements StopRegisterRepository {
    private final StopRegisterRepositoryJpa repositoryJpa;
    private final StopRegisterMapper mapper;
    private  final Logger logger = LoggerFactory.getLogger(StopRegisterRepositoryImpl.class);

    @Override
    public StopRegister create(StopRegister stopRegister) {
        StopRegisterEntity entity = mapper.toEntity(stopRegister);
        return mapper.toDomain(repositoryJpa.save(entity));
    }

    @Override
    public List<StopRegister> findByScheduleId(Long scheduleId) {
        List<StopRegisterEntity> listByScheduleId = repositoryJpa.findByScheduleId(scheduleId);
        return mapper.toDomainList(listByScheduleId);
    }

    @Override
    public void updateEntryTimeForRegisterBuStop(Long scheduleID, Long busStopId, Instant now) {
        int affectedRows = repositoryJpa.updateEntryTimeIfNull(scheduleID, busStopId, now);
        if (affectedRows > 0) {
            logger.info("Se ha actualizado la hora de entrada.");
        } else {
            logger.warn("No se ha encontrado ningún registro para actualizar.");
        }
    }

    @Override
    public void updateExitTimeForRegisterBusStop(Long scheduleId, Long busStopId, Instant now) {
        int affectedRows = repositoryJpa.updateExitTimeIfNull(scheduleId, busStopId, now);
        if (affectedRows > 0) {
            logger.info("Se ha actualizado la hora de salida.");
        } else {
            logger.warn("No se ha encontrado ningún registro para actualizar.");
        }
    }

    @Override
    public void updateAlertSend(Long scheduleId, Long busStopId, Boolean alertSend) {
        int affectedRows = repositoryJpa.updateAlerted(scheduleId, busStopId, alertSend);
        if (affectedRows > 0) {
            logger.info("Se ha actualizado la alerta enviada.");
        } else {
            logger.warn("No se ha podido actualizar");
        }
    }

    @Override
    public void updateTimeExceeded(Long scheduleId, Long busStopId, Boolean timeExceeded) {
        int affectedRows = repositoryJpa.updateTimeExceeded(scheduleId, busStopId, timeExceeded);
        if (affectedRows > 0) {
            logger.info("Se ha actualizado el tiempo excedido.");
        } else {
            logger.warn("El tiempo excedido no ha sido actualizado.");
        }
    }

    @Override
    public void updateMinimumTimeMet(Long scheduleId, Long busStopId, Boolean timeSpent) {
        int affectedRows = repositoryJpa.updateMinimumTimeMet(scheduleId, busStopId, timeSpent);
        if (affectedRows > 0) {
            logger.info("Se ha actualizado el tiempo el minimo tiempo.");
        } else {
            logger.warn("El tiempo excedido no ha sido actualizado nada.");
        }
    }

    @Override
    public void updateMaxTimeExcess(Long scheduleId, Long busStopId, Integer maxTimeExcess) {
        int affectedRows = repositoryJpa.updateMaxTimeExcess(scheduleId, busStopId, maxTimeExcess);
        if (affectedRows > 0) {
            logger.info("Se ha actualizado el tiempo el minimo tiempo.");
        } else {
            logger.warn("El tiempo excedido no ha sido actualizado nada.");
        }
    }

    @Override
    public void updateMinTimeShortfall(Long scheduleId, Long busStopId, Integer minTimeShortfall) {
        int affectedRows = repositoryJpa.updateMinTimeShortfall(scheduleId, busStopId, minTimeShortfall);
        if (affectedRows > 0) {
            logger.info("Se ha actualizado el tiempo el minimo tiempo.");
        } else {
            logger.warn("El tiempo excedido no ha sido actualizado nada.");
        }    }
}
