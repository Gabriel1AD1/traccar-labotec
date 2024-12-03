package com.labotec.traccar.domain.web.dto.labotec.response;

import lombok.Data;

@Data
public class OverviewPolylineResponse {
    private Long id;
    private Long routeBusStopId;
    private String polyline;
    private Boolean isPrimary;
}
