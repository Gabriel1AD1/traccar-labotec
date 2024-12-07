package com.labotec.traccar.infra.db.mysql.jpa.labotec.repository;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.OverviewPolylineEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.RouteBusStopEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.RouteEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.projection.OverviewPolylineProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface OverviewPolylineRepositoryJpa extends JpaRepository<OverviewPolylineEntity , Integer> {

    @Query("SELECT op FROM OverviewPolylineEntity op WHERE op.routeBusStop = :routeBusStop AND op.isPrimary = true")
    Optional<OverviewPolylineEntity> findByRouteBusStopAndIsPrimary(@Param("routeBusStop") RouteBusStopEntity routeBusStop);
    @Query("SELECT o.id as id, o.polyline as polyline, o.isPrimary as isPrimary " +
            "FROM OverviewPolylineEntity o WHERE o.routeBusStop.id = :routeBusStopId")
    List<OverviewPolylineProjection> findByRouteBusStopId(@Param("routeBusStopId") Long routeBusStopId);
}
