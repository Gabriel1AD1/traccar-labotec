package com.labotec.traccar.domain.web.dto.entel.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.labotec.traccar.domain.enums.STATE;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BusStopDTO {
    @Size(max = 200, message = "Name must not exceed 200 characters")
    @JsonProperty("name")
    private String name;
    @Size(max = 100, message = "Latitude must not exceed 100 characters")
    @NotEmpty(message = "latitude is required")
    @JsonProperty("latitude")
    private String latitude;
    @Size(max = 100, message = "Longitude must not exceed 100 characters")
    @NotEmpty(message = "longitude is required")
    @JsonProperty("longitude")
    private String longitude;
}
