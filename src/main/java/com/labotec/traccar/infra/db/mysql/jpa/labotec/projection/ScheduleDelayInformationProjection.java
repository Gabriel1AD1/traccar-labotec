package com.labotec.traccar.infra.db.mysql.jpa.labotec.projection;

import java.time.Instant;

public interface ScheduleDelayInformationProjection {
    Long getId();
    Instant getEstimatedArrivalTime();
    Instant getEstimatedDepartureTime();

}
