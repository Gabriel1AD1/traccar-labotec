package com.labotec.traccar.domain.web.dto.entel.create;

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
    private List<BusStopDTO> busStops;

    // Clase interna consolidada BusStopDTO
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BusStopDTO {

        @NotNull(message = "First Bus Stop ID is required")
        @Positive(message = "First Bus Stop ID must be a positive number")
        @JsonProperty("first_bus_stop_id")
        private Integer firstBusStopId;

        @NotNull(message = "Second Bus Stop ID is required")
        @Positive(message = "Second Bus Stop ID must be a positive number")
        @JsonProperty("second_bus_stop_id")
        private Integer secondBusStopId;

        @NotNull(message = "Order is required")
        @Positive(message = "Order must be a positive number")
        @JsonProperty("orden")
        private Integer order;

        @NotNull(message = "List of Polylines is required")
        @JsonProperty("polylines")
        private List<BusStopDTO.PolylineDTO> polylines; // Lista de polilíneas
        // Clase interna consolidada PolylineDTO
        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class PolylineDTO {
            @NotNull(message = "Polyline string is required") // Validación para el poliline
            @JsonProperty("polyline")
            private String polyline;

            @JsonProperty("is_primary") // Para identificar la polilínea principal
            private Boolean isPrimary;
        }
    }


}
