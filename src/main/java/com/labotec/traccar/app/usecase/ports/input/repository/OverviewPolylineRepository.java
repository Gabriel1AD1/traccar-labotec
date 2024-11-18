package com.labotec.traccar.app.usecase.ports.input.repository;

import com.labotec.traccar.app.utils.common.repository.GenericRepository;
import com.labotec.traccar.domain.database.models.BusStop;
import com.labotec.traccar.domain.database.models.OverviewPolyline;
import com.labotec.traccar.domain.database.models.RouteBusStop;

public interface OverviewPolylineRepository  {

    Iterable<OverviewPolyline> findAllByBusStop(BusStop busStop);

    OverviewPolyline findByRouteBusStopAndPrimary(RouteBusStop routeBusStopIterable);

    OverviewPolyline create(OverviewPolyline entity);
}