package com.labotec.traccar.domain.web.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RouteDTO {

    @NotNull(message = "Name is required")
    @Size(max = 250, message = "Name must not exceed 250 characters")
    private String name;

    @NotNull(message = "Status is required")
    private Byte status;

    private Integer originBusStopId;  // Optional: Refers to the origin bus stop id

    private Integer destinationBusStopId;  // Optional: Refers to the destination bus stop id

    @NotNull(message = "Company is required")
    private Integer companyId;  // Refers to the company id
}