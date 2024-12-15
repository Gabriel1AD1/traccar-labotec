package com.labotec.traccar.domain.web.labotec.request.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.labotec.traccar.domain.database.models.Company;
import com.labotec.traccar.domain.database.models.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
public class RouteBusStopIterableDTO {

    private User user;

    private Company company;

    @NotNull(message = "Route ID is required")
    @Positive(message = "Route ID must be a positive number")
    @JsonProperty("route_id")
    private Long routeId;

    @NotNull(message = "List of Bus Stops is required")
    @JsonProperty("bus_stops")
    private List<BusStopOrderDTO> busStops;

}
