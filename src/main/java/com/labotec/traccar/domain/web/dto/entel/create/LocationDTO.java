package com.labotec.traccar.domain.web.dto.entel.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.labotec.traccar.domain.database.models.Company;
import com.labotec.traccar.domain.database.models.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class LocationDTO {
    private User user;

    private Company company;

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

    @DecimalMin(value = "0.00", message = "Radius must be a positive value")
    @DecimalMax(value = "9999999.99", message = "Radius is too large")
    @JsonProperty("radius")  // Mapea con "radius" en el JSON
    private BigDecimal radius;

}
