package com.labotec.traccar.domain.web.dto.labotec.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResponseRouteBusStopInformation {

    @JsonProperty("first_bus_stop")
    private BusStopResponse fistBusStop;

    @JsonProperty("second_bus_stop")
    private BusStopResponse secondBusStop;

    @JsonProperty("overview_polylines")
    private List<ResponseOverviewPolyline> overviewPolylines;

    @JsonProperty("order")
    private Long order;
}
