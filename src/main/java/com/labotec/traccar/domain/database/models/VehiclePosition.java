package com.labotec.traccar.domain.database.models;

import lombok.Data;

import java.time.Instant;

@Data
public class VehiclePosition {
    private Long id;
    private Long deviceId;
    private Double latitude;
    private Double longitude;
    private Long scheduleId;
    private Instant currentTimeStoppedForBusStop;
    private Instant currentTimeStopped;
    private Integer minWaitTimeForBusStop;
    private Integer maxWaitTimeForBusStop;
    private boolean whereaboutsStatus;
    private Long currentSegment;
    private Long currentBusStop;
    private Long nexBusStopId;
    private boolean resetRoute;
    private boolean completeRoute;
    private Instant nextMinBusStopTimeBusStop;
    private Instant nextMaxBusStopTimeBusStop;

}
