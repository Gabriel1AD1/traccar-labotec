package com.labotec.traccar.domain.web.labotec.request.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Schema(description = "DTO for creating a new Bus Stop")
public class BusStopCreateDTO {

    @Size(max = 200, message = "Name must not exceed 200 characters")
    @JsonProperty("name")
    @Schema(description = "Name of the bus stop", example = "Main Street Station")
    private String name;

    @Size(max = 200, message = "Description must not exceed 200 characters")
    @JsonProperty("description")
    @Schema(description = "Description of the bus stop", example = "Bus stop located on Main Street, near the park")
    private String description;

    @Size(max = 100, message = "Latitude must not exceed 100 characters")
    @NotEmpty(message = "latitude is required")
    @JsonProperty("latitude")
    @Schema(description = "Latitude of the bus stop", example = "37.7749")
    private String latitude;

    @Size(max = 100, message = "Longitude must not exceed 100 characters")
    @NotEmpty(message = "longitude is required")
    @JsonProperty("longitude")
    @Schema(description = "Longitude of the bus stop", example = "-122.4194")
    private String longitude;
}
