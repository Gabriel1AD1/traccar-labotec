package com.labotec.traccar.infra.db.mysql.jpa.labotec.repository;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.RouteBusStopSegmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RouteBusStopSegmentRepositoryJpa extends JpaRepository<RouteBusStopSegmentEntity ,Long> {

    @Query("select r from RouteBusStopSegmentEntity r where r.route.id = :routeId")
    List<RouteBusStopSegmentEntity> findByRouteId(@Param("routeId") Long routeId);

}
