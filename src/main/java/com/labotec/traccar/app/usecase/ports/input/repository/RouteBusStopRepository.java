package com.labotec.traccar.app.usecase.ports.input.repository;

import com.labotec.traccar.app.utils.common.repository.GenericRepository;
import com.labotec.traccar.domain.database.models.Route;
import com.labotec.traccar.domain.database.models.RouteBusStop;

import java.util.List;

public interface RouteBusStopRepository extends GenericRepository<RouteBusStop, Integer> {

    Iterable<RouteBusStop> findByRoute(Route routeId);
    List<RouteBusStop> findByRouteOrderByOrder(Route routeId);

    Iterable<RouteBusStop> createList(List<RouteBusStop> routeBusStops);
}
