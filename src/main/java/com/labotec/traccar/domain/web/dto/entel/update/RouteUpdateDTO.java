package com.labotec.traccar.domain.web.dto.entel.update;

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
public class RouteUpdateDTO {

    @NotNull(message = "Route name is required")
    @JsonProperty("name")
    private String name;

    @NotNull(message = "Origin Bus Stop ID is required")
    @JsonProperty("origin_bus_stop_id")
    private Long originBusStopId;

    @NotNull(message = "Destination Bus Stop ID is required")
    @JsonProperty("destination_bus_stop_id")
    private Long destinationBusStopId;

    @NotNull(message = "List of Bus Stops is required")
    @JsonProperty("bus_stops")
    private List<BusStopDTO> busStops;

    // Clase interna BusStopOrderDTO
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BusStopDTO {
        @NotNull(message = "Company ID is required")
        @JsonProperty("id")
        private Long id;

        @NotNull(message = "First Bus Stop ID is required")
        @Positive(message = "First Bus Stop ID must be a positive number")
        @JsonProperty("first_bus_stop_id")
        private Long firstBusStopId;

        @NotNull(message = "Second Bus Stop ID is required")
        @Positive(message = "Second Bus Stop ID must be a positive number")
        @JsonProperty("second_bus_stop_id")
        private Long secondBusStopId;

        @NotNull(message = "Order is required")
        @Positive(message = "Order must be a positive number")
        @JsonProperty("orden")
        private Long order;
    }
}
