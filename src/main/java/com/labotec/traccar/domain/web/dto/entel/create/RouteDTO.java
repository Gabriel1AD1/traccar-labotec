package com.labotec.traccar.domain.web.dto.entel.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteDTO {


    @NotNull(message = "Route name is required")
    @JsonProperty("name")
    private String name;

    @NotNull(message = "Origin Bus Stop ID is required")
    @Positive
    @JsonProperty("origin_bus_stop_id")
    private Long originBusStopId;

    @NotNull(message = "Destination Bus Stop ID is required")
    @Positive
    @JsonProperty("destination_bus_stop_id")
    private Long destinationBusStopId;

    @NotNull(message = "List of Bus Stops is required")
    @JsonProperty("bus_stops")
    private List<CustomRouteBusStopDTO> busStops;



}
