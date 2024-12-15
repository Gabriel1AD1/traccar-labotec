package com.labotec.traccar.domain.web.labotec.request.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class LocationDTO {
    @NotNull(message = "Name is required")
    @Size(max = 200, message = "Name must not exceed 200 characters")
    @JsonProperty("name")  // Mapea con "name" en el JSON
    private String name;

    @Size(max = 100, message = "Latitude must not exceed 100 characters")
    @JsonProperty("latitude")  // Mapea con "latitude" en el JSON
    private String latitude;

    @Size(max = 100, message = "Longitude must not exceed 100 characters")
    @JsonProperty("longitude")  // Mapea con "longitude" en el JSON
    private String longitude;

    @NotNull
    @NotBlank
    @JsonProperty("description")
    private String description;
    @NotNull
    @Positive
    @JsonProperty("bus_stop_associate_id")
    private Long busStopAssociateId;
    @DecimalMin(value = "0.00", message = "Radius must be a positive value")
    @DecimalMax(value = "9999999.99", message = "Radius is too large")
    @JsonProperty("radius")  // Mapea con "radius" en el JSON
    private BigDecimal radius;

}
