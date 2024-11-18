package com.labotec.traccar.infra.db.mysql.jpa.labotec.repository;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepositoryJpa extends JpaRepository<CompanyEntity, Long> {
    // Consultas personalizadas si es necesario
}
