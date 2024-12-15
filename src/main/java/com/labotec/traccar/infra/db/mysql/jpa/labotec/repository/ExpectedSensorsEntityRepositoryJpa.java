package com.labotec.traccar.infra.db.mysql.jpa.labotec.repository;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.ExpectedSensorsEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.projection.ResponseExpectedSensorsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ExpectedSensorsEntityRepositoryJpa extends JpaRepository<ExpectedSensorsEntity, Long> {

    Optional<ExpectedSensorsEntity> findByIdAndUserId(Long resourceId, Long userId);

    List<ExpectedSensorsEntity> findAllByUserId(Long userId);

    int deleteByIdAndUserId(Long resourceId, Long userId);


    @Query("""
        SELECT
            e.id as id,
            e.descriptionSensor AS descriptionSensor,
            e.nameSensor AS nameSensor,
            e.typeSensor AS typeSensor,
            e.dataType AS dataType
        FROM ExpectedSensorsEntity e
        WHERE e.deviceId = :deviceId AND e.userId = :userId
    """)
    List<ResponseExpectedSensorsProjection> findAllByDeviceIdAndUserId(Long deviceId, Long userId);

    Optional<ExpectedSensorsEntity> findByDeviceIdAndNameSensorAndUserId(Long deviceId, String name, Long userId);

    Optional<ExpectedSensorsEntity> findByIdAndDeviceId(Long sensorId, Long deviceId);

}