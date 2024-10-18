package com.labotec.traccar.domain.web.dto;

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

    @NotNull(message = "Name is required")
    @Size(max = 200, message = "Name must not exceed 200 characters")
    private String name;

    @Size(max = 100, message = "Latitude must not exceed 100 characters")
    private String latitude;

    @Size(max = 100, message = "Longitude must not exceed 100 characters")
    private String longitude;

    @DecimalMin(value = "0.00", message = "Radius must be a positive value")
    @DecimalMax(value = "9999999.99", message = "Radius is too large")
    private BigDecimal radius;

    @NotNull(message = "Company is required")
    private Integer companyId;  // Refers to the company id
}
