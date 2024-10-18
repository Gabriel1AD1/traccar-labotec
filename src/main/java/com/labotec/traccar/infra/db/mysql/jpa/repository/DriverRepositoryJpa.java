package com.labotec.traccar.infra.db.mysql.jpa.repository;

import com.labotec.traccar.infra.db.mysql.jpa.entity.DriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepositoryJpa extends JpaRepository<DriverEntity, Integer> {
    // Aquí puedes agregar consultas personalizadas si es necesario
}
