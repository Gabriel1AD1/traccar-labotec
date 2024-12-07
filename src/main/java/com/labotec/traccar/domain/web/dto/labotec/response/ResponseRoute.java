package com.labotec.traccar.domain.web.dto.labotec.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.labotec.traccar.app.enums.RouteType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseRoute {

    @JsonProperty("id")  // Snake case
    private Long id;

    @JsonProperty("name")  // Snake case
    private String name;

    @JsonProperty("distance_max_in_km")  // Snake case
    private Long distanceMaxInKM;

    @JsonProperty("distance_min_in_km")  // Snake case
    private Long distanceMinInKM;

    @JsonProperty("route_type")  // Snake case
    private RouteType routeType;
}
