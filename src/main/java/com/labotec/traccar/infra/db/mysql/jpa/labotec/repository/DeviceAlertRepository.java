package com.labotec.traccar.infra.db.mysql.jpa.labotec.repository;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.DeviceAlertEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceAlertRepository extends JpaRepository<DeviceAlertEntity, Long> {
}
