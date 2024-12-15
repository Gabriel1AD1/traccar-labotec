package com.labotec.traccar.infra.db.mysql.jpa.labotec.repository;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.ErrorLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ErrorLogEntityRepository extends JpaRepository<ErrorLogEntity, Long> {
    /**
     * Elimina los registros más antiguos que la fecha proporcionada.
     *
     * @param timestamp Fecha límite para eliminar registros antiguos.
     * @return El número de registros eliminados.
     */
    int deleteByTimestampBefore(LocalDateTime timestamp);}