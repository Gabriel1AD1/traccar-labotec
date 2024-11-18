package com.labotec.traccar.infra.db.mysql.jpa.traccar.repository;

import com.labotec.traccar.infra.db.mysql.jpa.traccar.entity.TcDevice;
import com.labotec.traccar.infra.db.mysql.jpa.traccar.projections.TcDeviceBasicProjection;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;

public interface TcDeviceRepository extends JpaRepository<TcDevice, Integer> {
    List<TcDeviceBasicProjection> findByIdNotIn(List<Integer> ids);

    @Query("SELECT d.isStopped FROM TcDevice d WHERE d.id = :deviceId")
    boolean isVehicleStopped(Integer deviceId);
    @Query("SELECT d.alertSent FROM TcDevice d WHERE d.id = :deviceId")
    boolean isAlertSent(Integer deviceId);

    @Modifying
    @Transactional
    @Query("UPDATE TcDevice d SET d.isStopped = TRUE, d.alertSent = FALSE, d.lastStoppedTime = :lastStoppedTime WHERE d.id = :deviceId")
    void markVehicleAsStopped(Integer deviceId, Instant lastStoppedTime);

    @Query("SELECT CASE WHEN (d.lastStoppedTime IS NOT NULL AND TIMESTAMPDIFF(MINUTE, d.lastStoppedTime, CURRENT_TIMESTAMP) >= 10) THEN TRUE ELSE FALSE END FROM TcDevice d WHERE d.id = :deviceId AND d.isStopped = TRUE AND d.alertSent = FALSE")
    boolean shouldSendAlert(Integer deviceId);

    @Modifying
    @Transactional
    @Query("UPDATE TcDevice d SET d.alertSent = TRUE WHERE d.id = :deviceId")
    void setAlertSentTrue(Integer deviceId);

    @Modifying
    @Transactional
    @Query("UPDATE TcDevice d SET d.alertSent = TRUE WHERE d.id = :deviceId")
    void markAlertAsSent(Integer deviceId);

    @Modifying
    @Transactional

    @Query("UPDATE TcDevice d SET d.isStopped = FALSE, d.alertSent = FALSE, d.lastStoppedTime = NULL WHERE d.id = :deviceId")
    void resetVehicleState(Integer deviceId);
    @Query("SELECT d.lastStoppedTime FROM TcDevice d WHERE d.id = :deviceId")
    Instant getLastStoppedTime(Integer deviceId);
}
