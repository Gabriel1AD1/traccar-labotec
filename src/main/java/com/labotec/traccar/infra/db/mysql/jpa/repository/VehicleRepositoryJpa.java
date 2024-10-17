package com.labotec.traccar.infra.db.mysql.jpa.repository;

import com.labotec.traccar.infra.db.mysql.jpa.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepositoryJpa extends JpaRepository<VehicleEntity, Integer> {
    // Consultas personalizadas si es necesario
}
