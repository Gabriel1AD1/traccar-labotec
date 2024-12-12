package com.labotec.traccar.domain.database.models.read;

import lombok.Data;

import java.time.Instant;

@Data
public class InformationRoute {
    private long userId;
    private long routeId;
    private long scheduleId;
    private Instant estimatedDepartureTime;
    private Instant estimatedArrivedTime;
}
