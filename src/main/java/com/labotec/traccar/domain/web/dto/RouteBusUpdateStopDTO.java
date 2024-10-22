package com.labotec.traccar.domain.web.dto;

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
public class RouteBusUpdateStopDTO {


    @NotNull(message = "Route ID is required")
    @Positive(message = "Route ID must be a positive number")
    @JsonProperty("route_id")
    private Integer routeId;

    @NotNull(message = "List of Bus Stops is required")
    @JsonProperty("bus_stops")
    private List<RouteBusUpdateStopDTO.BusStopOrderUpdateDTO> busStops;

    // Clase interna BusStopOrderDTO
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BusStopOrderUpdateDTO {
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
