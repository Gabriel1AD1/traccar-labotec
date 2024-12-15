package com.labotec.traccar.domain.web.labotec.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseOverviewPolyline {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("polyline")
    private String polyline;

    @JsonProperty("is_primary")
    private Boolean isPrimary;
}
