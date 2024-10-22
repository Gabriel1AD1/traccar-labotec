package com.labotec.traccar.app.usecase.ports.input;

import com.labotec.traccar.app.utils.common.repository.GenericRepository;
import com.labotec.traccar.domain.database.models.RouteBusStop;
import com.labotec.traccar.domain.web.dto.RouteBusStopDTO;

import java.util.List;

public interface RouteBusStopRepository extends GenericRepository<RouteBusStop, Integer> {

    Iterable<RouteBusStop> findByRoute(Integer routeId);

    Iterable<RouteBusStop> createList(List<RouteBusStop> routeBusStops);
}
