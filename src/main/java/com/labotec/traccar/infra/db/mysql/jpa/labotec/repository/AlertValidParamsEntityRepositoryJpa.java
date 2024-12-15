package com.labotec.traccar.infra.db.mysql.jpa.labotec.repository;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.AlertValidParamsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertValidParamsEntityRepositoryJpa extends JpaRepository<AlertValidParamsEntity, Long> {
    List<AlertValidParamsEntity> findByDeviceId(Long deviceId);
}