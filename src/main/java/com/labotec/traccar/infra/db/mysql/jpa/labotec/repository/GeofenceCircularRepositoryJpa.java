package com.labotec.traccar.infra.db.mysql.jpa.labotec.repository;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.CircularGeofenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeofenceCircularRepositoryJpa extends JpaRepository<CircularGeofenceEntity, Long> {
}