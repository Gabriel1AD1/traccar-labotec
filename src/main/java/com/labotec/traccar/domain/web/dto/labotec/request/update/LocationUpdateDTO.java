package com.labotec.traccar.domain.web.dto.labotec.request.update;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class LocationUpdateDTO {

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

    @NotNull(message = "Company is required")
    @JsonProperty("company_id")  // Mapea con "company_id" en el JSON
    private Integer companyId;  // Refers to the company id
}
