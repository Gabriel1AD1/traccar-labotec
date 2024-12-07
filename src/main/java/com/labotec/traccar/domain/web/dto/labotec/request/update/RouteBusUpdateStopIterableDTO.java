package com.labotec.traccar.domain.web.dto.labotec.request.update;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteBusUpdateStopIterableDTO {


    @NotNull(message = "Route ID is required")
    @Positive(message = "Route ID must be a positive number")
    @JsonProperty("route_id")
    private Long routeId;

    @NotNull(message = "List of Bus Stops is required")
    @JsonProperty("bus_stops")
    private List<UpdateBusStopOrderDTO> busStops;

}
