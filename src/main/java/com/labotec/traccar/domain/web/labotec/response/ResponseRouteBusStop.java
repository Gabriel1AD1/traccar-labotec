package com.labotec.traccar.domain.web.labotec.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ResponseRouteBusStop {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("route_id")
    private Long routeId;

    @JsonProperty("first_bus_stop_id")
    private Long firstBusStopId;

    @JsonProperty("second_bus_stop_id")
    private Long secondBusStopId;

    @JsonProperty("overview_polylines")
    private List<ResponseOverviewPolyline> overviewPolylines;
}
