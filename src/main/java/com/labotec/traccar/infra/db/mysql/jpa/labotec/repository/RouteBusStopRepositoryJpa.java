package com.labotec.traccar.infra.db.mysql.jpa.labotec.repository;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.RouteBusStopEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.RouteEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.projection.RouteBusStopInformationProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface RouteBusStopRepositoryJpa extends JpaRepository<RouteBusStopEntity , Long> {

    Iterable<RouteBusStopEntity> findByRoute(RouteEntity route);

    List<RouteBusStopEntity> findByRouteOrderByOrder(RouteEntity route);
    @Query("SELECT rbs.id as id, rbs.order as order, rbs.firstBusStop.id as firstBusStopId, rbs.secondBusStop.id as secondBusStopId " +
            "FROM RouteBusStopEntity rbs " +
            "WHERE rbs.route.id = :routeId")
    List<RouteBusStopInformationProjection> findByRouteId(@Param("routeId") Long routeId);
}
