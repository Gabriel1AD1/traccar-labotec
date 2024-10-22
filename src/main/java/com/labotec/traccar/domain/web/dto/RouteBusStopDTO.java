package com.labotec.traccar.domain.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteBusStopDTO {


    @NotNull(message = "Route ID is required")
    @Positive(message = "Route ID must be a positive number")
    @JsonProperty("route_id")
    private Integer routeId;

    @NotNull(message = "List of Bus Stops is required")
    @JsonProperty("bus_stops")
    private List<RouteDTO.BusStopOrderDTO> busStops;

    // Clase interna BusStopOrderDTO
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BusStopOrderDTO {
        @NotNull(message = "Bus Stop Route  ID is required")
        private Integer id;

        @NotNull(message = "Bus Stop ID is required")
        @Positive(message = "Bus Stop ID must be a positive number")
        @JsonProperty("bus_stop_id")
        private Integer busStopId;

        @NotNull(message = "Order is required")
        @Positive(message = "Order must be a positive number")
        @JsonProperty("orden")
        private Integer order;
    }
}
