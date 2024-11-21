package com.labotec.traccar.infra.db.mysql.jpa.labotec.repository;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.RouteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RouteRepositoryJpa extends JpaRepository<RouteEntity, Integer> {
    @Query("SELECT r FROM RouteEntity r WHERE r.userId.id = :userId AND r.id = :routeId")
    Optional<RouteEntity> findRouteByUserIdAndRouteId(@Param("userId") Long userId, @Param("routeId") Long routeId);}
