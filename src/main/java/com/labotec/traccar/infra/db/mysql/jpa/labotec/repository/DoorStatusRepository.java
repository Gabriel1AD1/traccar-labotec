package com.labotec.traccar.infra.db.mysql.jpa.labotec.repository;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.process.DoorStatusEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.process.DoorStatusId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoorStatusRepository extends JpaRepository<DoorStatusEntity, DoorStatusId> {
    Optional<DoorStatusEntity> findById_DeviceIdAndId_DoorId(Long deviceId, String doorId);
}