package com.labotec.traccar.domain.web.dto.labotec.request.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BusStopCreateDTO {
    @Size(max = 200, message = "Name must not exceed 200 characters")
    @JsonProperty("name")
    private String name;
    @Size(max = 200, message = "Name must not exceed 200 characters")
    @JsonProperty("description")
    private String description;
    @Size(max = 100, message = "Latitude must not exceed 100 characters")
    @NotEmpty(message = "latitude is required")
    @JsonProperty("latitude")
    private String latitude;
    @Size(max = 100, message = "Longitude must not exceed 100 characters")
    @NotEmpty(message = "longitude is required")
    @JsonProperty("longitude")
    private String longitude;
}
