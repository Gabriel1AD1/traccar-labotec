package com.labotec.traccar.infra.db.mysql.jpa.labotec.repository;


import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.GeofenceCircularEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeofenceCircularRepositoryJpa extends JpaRepository<GeofenceCircularEntity, Long> {
}