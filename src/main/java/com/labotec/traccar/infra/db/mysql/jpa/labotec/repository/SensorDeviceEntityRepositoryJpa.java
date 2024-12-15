package com.labotec.traccar.infra.db.mysql.jpa.labotec.repository;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.SensorDeviceEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.projection.OptimizedSensorDeviceProjection;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.projection.ResponseSensorDeviceProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SensorDeviceEntityRepositoryJpa extends JpaRepository<SensorDeviceEntity, Long> {
    @Query("SELECT s.deviceId AS sensorName, s.typeSensor AS typeSensor " +
            "FROM SensorDeviceEntity s WHERE s.deviceId = :deviceId")
    List<OptimizedSensorDeviceProjection> findSensorNamesAndTypesByDeviceId(@Param("deviceId") Long deviceId);
    @Query(name = "UpdateSensorStateAndReturn")
    Optional<SensorDeviceEntity> updateSensorStateAndReturn(
            @Param("p_dispositivo_id") Long deviceId,
            @Param("p_nombre_sensor") String nameSensor,
            @Param("p_estado_nuevo") String stateCurrent
    );

    // Consulta personalizada que devuelve la proyección como una interfaz
    @Query("SELECT sd.sensorName AS sensorName, " +
            "sd.stateCurrent AS stateCurrent, " +
            "sd.typeSensor AS typeSensor, " +
            "sd.initStateCurrent AS initStateCurrent, " +
            "sd.timeAcumulated AS timeAcumulated " +
            "FROM SensorDeviceEntity sd WHERE sd.deviceId = :deviceId")
    List<ResponseSensorDeviceProjection> findByDeviceId(@Param("deviceId") Long deviceId);

    int deleteByDeviceIdAndId(@Param("deviceId") Long deviceId,@Param("resourceId") Long resourceId);
}