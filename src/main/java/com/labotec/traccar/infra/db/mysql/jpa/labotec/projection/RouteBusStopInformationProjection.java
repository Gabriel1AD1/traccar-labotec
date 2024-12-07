package com.labotec.traccar.infra.db.mysql.jpa.labotec.projection;

public interface RouteBusStopInformationProjection
{
    Long getId();
    Long getOrder();
    Long getFirstBusStopId();
    Long getSecondBusStopId();
}
