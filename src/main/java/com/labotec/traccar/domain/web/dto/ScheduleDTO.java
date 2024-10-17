package com.labotec.traccar.domain.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ScheduleDTO {

    @NotNull(message = "Departure time is required")
    private Instant departureTime;

    @NotNull(message = "Arrival time is required")
    private Instant arrivalTime;

    @NotNull(message = "Vehicle is required")
    private Integer vehicleId;  // Refers to the vehicle id

    @NotNull(message = "Driver is required")
    private Integer driverId;  // Refers to the driver id

    @NotNull(message = "Location ID is required")
    private Integer locationId;

    @NotNull(message = "Route is required")
    private Integer routeId;  // Refers to the route id

    private String sheetNumber;

    @NotNull(message = "Status is required")
    private Byte status;

    @NotNull(message = "Estimated departure time is required")
    private Instant estimatedDepartureTime;

    @NotNull(message = "Estimated arrival time is required")
    private Instant estimatedArrivalTime;

    @NotNull(message = "Company is required")
    private Integer companyId;  // Refers to the company id
}