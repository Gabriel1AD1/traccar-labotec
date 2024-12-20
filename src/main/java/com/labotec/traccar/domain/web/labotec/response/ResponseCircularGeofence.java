package com.labotec.traccar.domain.web.labotec.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseCircularGeofence {

    @JsonProperty("name")
    private String name;

    @JsonProperty("latitude")
    private Double latitude;

    @JsonProperty("longitude")
    private Double longitude;

    @JsonProperty("radius")
    private Double radius;
}
