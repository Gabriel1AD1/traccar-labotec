package com.labotec.traccar.domain.web.dto.labotec.request.update;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBusStopOrderDTO
{
    @NotNull(message = "Bus Stop Route  ID is required")
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
