package com.labotec.traccar.domain.web.dto.labotec.response;

import com.labotec.traccar.domain.database.models.BusStop;
import lombok.Data;

import java.util.List;

@Data
public class RouteBusStopResponse {
    private Long id;
    private Long routeId;
    private Long firstBusStopId;
    private Long secondBusStopId;
    private List<OverviewPolylineResponse> overviewPolylines;
    private Integer estimatedTravelTime; // En minutos
    private Integer maxWaitTime; // En minutos
    private Integer minWaitTime; // En minutos
}
