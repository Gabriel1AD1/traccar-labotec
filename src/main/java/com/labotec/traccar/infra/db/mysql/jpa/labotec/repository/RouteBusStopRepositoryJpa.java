package com.labotec.traccar.infra.db.mysql.jpa.labotec.repository;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.RouteBusStopEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.RouteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteBusStopRepositoryJpa extends JpaRepository<RouteBusStopEntity , Integer> {
    void deleteByRouteId(Integer id);

    Iterable<RouteBusStopEntity> findByRoute(RouteEntity route);

    List<RouteBusStopEntity> findByRouteOrderByOrder(RouteEntity route);

}
