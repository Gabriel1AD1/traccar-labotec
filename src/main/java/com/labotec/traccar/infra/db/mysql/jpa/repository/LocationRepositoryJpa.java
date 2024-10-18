package com.labotec.traccar.infra.db.mysql.jpa.repository;

import com.labotec.traccar.infra.db.mysql.jpa.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepositoryJpa extends JpaRepository<LocationEntity, Integer> {
}
