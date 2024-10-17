package com.labotec.traccar.infra.db.mysql.jpa.repository;

import com.labotec.traccar.infra.db.mysql.jpa.entity.RouteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepositoryJpa extends JpaRepository<RouteEntity, Integer> {
    // Consultas personalizadas si es necesario
}
