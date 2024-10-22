package com.labotec.traccar.infra.db.mysql.jpa.labotec.repository;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.GeofencePoligonalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeofencePoligonalRepositoryJpa extends JpaRepository<GeofencePoligonalEntity, Long> {
}