package com.labotec.traccar.infra.db.mysql.jpa.repository;

import com.labotec.traccar.infra.db.mysql.jpa.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepositoryJpa extends JpaRepository<ScheduleEntity, Integer> {
    // Consultas personalizadas si es necesario
}
