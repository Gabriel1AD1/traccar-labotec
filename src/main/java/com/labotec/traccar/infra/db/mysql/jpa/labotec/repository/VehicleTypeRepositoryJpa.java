package com.labotec.traccar.infra.db.mysql.jpa.labotec.repository;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.VehicleTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleTypeRepositoryJpa extends JpaRepository<VehicleTypeEntity , Integer> {
}
