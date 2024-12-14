package com.labotec.traccar.app.ports.input.repository;

import com.labotec.traccar.domain.database.models.BusStop;
import com.labotec.traccar.domain.database.models.OverviewPolyline;
import com.labotec.traccar.domain.database.models.RouteBusStop;
import com.labotec.traccar.domain.query.OptimizedOverviewPolyline;
import com.labotec.traccar.domain.web.dto.labotec.response.ResponseOverviewPolyline;

import java.util.List;

public interface OverviewPolylineRepository  {

    Iterable<OverviewPolyline> findAllByBusStop(BusStop busStop);

    OverviewPolyline findByRouteBusStopAndPrimary(RouteBusStop routeBusStopIterable);

    OverviewPolyline create(OverviewPolyline entity);

    List<ResponseOverviewPolyline> findByRouteBusStopId(Long routeBusStopId);

    List<OptimizedOverviewPolyline> findPrimaryPolylinesByRouteId(Long routeId);
}
