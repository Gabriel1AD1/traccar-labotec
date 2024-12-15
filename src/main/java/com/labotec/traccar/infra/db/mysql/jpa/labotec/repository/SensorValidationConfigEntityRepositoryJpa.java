package com.labotec.traccar.infra.db.mysql.jpa.labotec.repository;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.SensorValidationConfigEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.projection.ResponseSensorValidatorConfigProjection;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.projection.SensorValidationConfigProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SensorValidationConfigEntityRepositoryJpa extends JpaRepository<SensorValidationConfigEntity, Long> {

    @Query("SELECT s.id, s.userId, s.nameSensor AS nameSensor, s.dataType as dataType, s.operator AS operator, s.value AS value ,s.typeValidation as typeValidation ,s.messageAlert as messageAlert " +
            "FROM SensorValidationConfigEntity s WHERE  s.state = true AND s.deviceId  = :deviceId")
    List<SensorValidationConfigProjection> findValidationConfigsByDeviceId(@Param("deviceId") Long deviceId);


    int deleteByDeviceId(Long deviceId);

    int deleteByIdAndUserId(Long resourceId, Long userId);

    Optional<SensorValidationConfigEntity> findByIdAndUserId(Long resourceId, Long userId);

    @Query("SELECT s.nameSensor AS nameSensor, " +
            "s.operator AS operator, " +
            "s.value AS value, " +
            "s.messageAlert AS messageAlert, " +
            "s.dataType AS dataType " +
            "FROM SensorValidationConfigEntity s " +
            "WHERE s.deviceId = :deviceId " +
            "AND s.userId = :userId")
    List<ResponseSensorValidatorConfigProjection> findByDeviceIdAndUserId(
            @Param("deviceId") Long deviceId,
            @Param("userId") Long userId);
}