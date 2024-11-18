package com.labotec.traccar.domain.web.dto.entel.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.labotec.traccar.app.Validations.SinglePrimaryPolyline;
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
public class CustomRouteBusStopDTO
{
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

    @NotNull(message = "List of Polylines is required")
    @JsonProperty("polylines")
    @SinglePrimaryPolyline
    private List<PolylineDTO> polylines;
}
