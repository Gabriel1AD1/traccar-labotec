package com.labotec.traccar.domain.database.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProgressRoute {
    private Long routeBusStopId;

    private Double percentage;
}
