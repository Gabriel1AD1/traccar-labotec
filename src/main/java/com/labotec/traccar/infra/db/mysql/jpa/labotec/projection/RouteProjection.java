package com.labotec.traccar.infra.db.mysql.jpa.labotec.projection;

import java.util.List;

public interface RouteProjection {
    Long getId();
    List<RouteBusStopProjection> getBusStopsList();
    String getName();
}