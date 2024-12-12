package com.labotec.traccar.domain.web.dto.labotec.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.labotec.traccar.domain.enums.TypeBusStop;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ResponseRouteBusStopSegment {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("route_id")
    private Long routeId;

    @JsonProperty("bus_stop_id")
    private Long busStopId;

    @JsonProperty("estimated_travel_time")
    private Integer estimatedTravelTime; // En minutos

    @JsonProperty("max_wait_time")
    private Integer maxWaitTime; // En minutos

    @JsonProperty("min_wait_time")
    private Integer minWaitTime; // En minutos

    @JsonProperty("order")
    private Long order;

    @JsonProperty("type_bus_stop")
    private TypeBusStop typeBusStop;
}
