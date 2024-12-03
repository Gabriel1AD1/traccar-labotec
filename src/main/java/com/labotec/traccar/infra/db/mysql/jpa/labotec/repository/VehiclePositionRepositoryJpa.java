package com.labotec.traccar.infra.db.mysql.jpa.labotec.repository;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.VehiclePositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiclePositionRepositoryJpa extends JpaRepository<VehiclePositionEntity, Long> {
    VehiclePositionEntity findByScheduleId(Long routeId);
}