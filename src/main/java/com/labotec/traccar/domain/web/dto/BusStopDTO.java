package com.labotec.traccar.domain.web.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusStopDTO {

    @NotNull(message = "Name is required")
    @Size(max = 200, message = "Name must not exceed 200 characters")
    private String name;

    @Size(max = 100, message = "Latitude must not exceed 100 characters")
    private String latitude;

    @Size(max = 100, message = "Longitude must not exceed 100 characters")
    private String longitude;

    @NotNull(message = "Status is required")
    private Byte status;

    @NotNull(message = "Company is required")
    private Integer companyId;  // Refers to the company id
}
