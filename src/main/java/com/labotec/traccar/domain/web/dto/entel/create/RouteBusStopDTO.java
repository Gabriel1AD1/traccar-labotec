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
public class RouteBusStopDTO {
    private Long userId;

    @NotNull(message = "Route ID is required")
    @Positive(message = "Route ID must be a positive number")
    @JsonProperty("route_id")
    private Integer routeId;

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

}
