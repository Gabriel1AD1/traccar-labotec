package com.labotec.traccar.infra.db.mysql.jpa.labotec.repository;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.process.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceRepositoryJpa extends JpaRepository<DeviceEntity , Long> {
    Optional<DeviceEntity> findByTraccarDeviceId(long traccarDeviceId);
}
