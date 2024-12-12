package com.labotec.traccar.infra.db.mysql.jpa.labotec.repository;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.AlertEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.projection.ResponseAlertProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlertEntityRepositoryJpa extends JpaRepository<AlertEntity, Long> {
    @Query("SELECT a.alertType AS alertType, a.description AS description, a.latitude AS latitude, " +
            "a.longitude AS longitude, a.createdAt AS createdAt " +
            "FROM AlertEntity a WHERE a.userId = :userId " +
            "ORDER BY a.createdAt DESC")
    List<ResponseAlertProjection> findTop10ByUserId(@Param("userId") Long userId);
    @Query("SELECT a.alertType AS alertType, a.description AS description, a.latitude AS latitude, " +
            "a.longitude AS longitude, a.createdAt AS createdAt " +
            "FROM AlertEntity a WHERE a.userId = :userId " +
            "ORDER BY a.createdAt DESC")
    List<ResponseAlertProjection> findAll(@Param("userId") Long userId);
}