package com.labotec.traccar.domain.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteDTO {

    @NotNull(message = "Company ID is required")
    @JsonProperty("company_id")
    private Integer companyId;

    @NotNull(message = "Route name is required")
    @JsonProperty("name")
    private String name;

    @NotNull(message = "Route status is required")
    @JsonProperty("status")
    private Byte status;

    @NotNull(message = "Origin Bus Stop ID is required")
    @JsonProperty("origin_bus_stop_id")
    private Integer originBusStopId;

    @NotNull(message = "Destination Bus Stop ID is required")
    @JsonProperty("destination_bus_stop_id")
    private Integer destinationBusStopId;

    @NotNull(message = "List of Bus Stops is required")
    @JsonProperty("bus_stops")
    private List<BusStopOrderDTO> busStops;

    // Clase interna BusStopOrderDTO
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BusStopOrderDTO {

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
