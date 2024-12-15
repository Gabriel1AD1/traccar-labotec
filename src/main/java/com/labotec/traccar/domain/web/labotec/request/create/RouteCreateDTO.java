package com.labotec.traccar.domain.web.labotec.request.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.labotec.traccar.app.Validations.ConsistentBusStops;
import com.labotec.traccar.app.Validations.SinglePrimaryPolyline;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ConsistentBusStops
@SinglePrimaryPolyline
public class RouteCreateDTO {
    @NotNull(message = "Route name is required")
    @NotBlank(message = "Route name is required and cannot be empty.")
    @JsonProperty("name")
    private String name;
    @NotNull(message = "Description is required")
    @JsonProperty("description")
    private String description;
    @JsonProperty("distance_max_in_km")
    @NotNull
    private Long distanceMaxInKM;
    @JsonProperty("distance_min_in_km")
    @NotNull
    private Long distanceMinInKM;
    @NotNull(message = "List of Bus Stops is required")
    @JsonProperty("bus_stops")
    private List<RouteBusStopCreateDTO> segments;
    @JsonProperty("bus_stop_segments_list")
    private List<RouteSegmentCreateDTO> segmentsBusStop;
}
