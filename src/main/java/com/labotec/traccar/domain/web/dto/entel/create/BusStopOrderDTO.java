package com.labotec.traccar.domain.web.dto.entel.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class BusStopOrderDTO {
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
