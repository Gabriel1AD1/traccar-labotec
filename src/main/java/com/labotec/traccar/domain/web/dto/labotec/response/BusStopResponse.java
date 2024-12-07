package com.labotec.traccar.domain.web.dto.labotec.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.labotec.traccar.domain.enums.STATE;
import lombok.Data;

@Data
public class BusStopResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("latitude")
    private Double latitude;

    @JsonProperty("longitude")
    private Double longitude;

    @JsonProperty("status")
    private STATE status;
}
