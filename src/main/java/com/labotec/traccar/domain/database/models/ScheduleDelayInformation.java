package com.labotec.traccar.domain.database.models;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;


@Data
@Builder
public class ScheduleDelayInformation {
    private Long id;
    private Instant estimatedDepartureTime;
    private  Instant estimatedArrivalTime;

}
