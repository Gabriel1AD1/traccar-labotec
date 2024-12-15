package com.labotec.traccar.domain.web.labotec.request.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.labotec.traccar.domain.enums.TypeBusStop;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;


@Data
public class RouteSegmentCreateDTO {
    @NotNull(message = "First Bus Stop ID is required")
    @Positive(message = "First Bus Stop ID must be a positive number")
    @JsonProperty("start_bus_stop_id")
    private Long startBusStopId;

    @NotNull(message = "Order is required")
    @Positive(message = "Order must be a positive number")
    @JsonProperty("order")
    private Long order;

    @JsonProperty("estimated_next_time_to_bus_stop")
    private Integer estimatedNextTravelTime; // En minutos


    @JsonProperty("min_wait_time")
    private Integer minWaitTime; // En minutos

    @JsonProperty("max_wait_time")
    private Integer maxWaitTime; // En minutos

    @JsonProperty("orden_type_bus_stop")
    private TypeBusStop typeBusStop;

}
