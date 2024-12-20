package com.labotec.traccar.infra.db.mysql.jpa.labotec.projection;

import java.time.Instant;

public interface InformationScheduleProjection {
    Long getUserId();
    Long getRouteId();
    Long getScheduleId();
    Instant getEstimatedDepartureTime();
    Instant getEstimatedArrivalTime();
}
