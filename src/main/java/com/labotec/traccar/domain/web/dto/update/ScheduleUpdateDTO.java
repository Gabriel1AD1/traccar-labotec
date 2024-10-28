package com.labotec.traccar.domain.web.dto.update;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ScheduleUpdateDTO {

    @NotNull(message = "Departure time is required")
    @JsonProperty("departure_time")  // Mapea con "departure_time" en el JSON
    private Instant departureTime;

    @NotNull(message = "Arrival time is required")
    @JsonProperty("arrival_time")  // Mapea con "arrival_time" en el JSON
    private Instant arrivalTime;

    @NotNull(message = "Vehicle is required")
    @JsonProperty("vehicle_id")  // Mapea con "vehicle_id" en el JSON
    private Integer vehicleId;  // Refers to the vehicle id

    @NotNull(message = "Driver is required")
    @JsonProperty("driver_id")  // Mapea con "driver_id" en el JSON
    private Integer driverId;  // Refers to the driver id

    @NotNull(message = "Location ID is required")
    @JsonProperty("location_id")  // Mapea con "location_id" en el JSON
    private Integer locationId;

    @NotNull(message = "Route is required")
    @JsonProperty("route_id")  // Mapea con "route_id" en el JSON
    private Integer routeId;  // Refers to the route id

    @JsonProperty("sheet_number")  // Mapea con "sheet_number" en el JSON
    private String sheetNumber;

    @NotNull(message = "Status is required")
    @JsonProperty("status")  // Mapea con "status" en el JSON
    private Byte status;

    @NotNull(message = "Estimated departure time is required")
    @JsonProperty("estimated_departure_time")  // Mapea con "estimated_departure_time" en el JSON
    private Instant estimatedDepartureTime;

    @NotNull(message = "Estimated arrival time is required")
    @JsonProperty("estimated_arrival_time")  // Mapea con "estimated_arrival_time" en el JSON
    private Instant estimatedArrivalTime;

    @NotNull(message = "Company is required")
    @JsonProperty("company_id")  // Mapea con "company_id" en el JSON
    private Integer companyId;  // Refers to the company id
}
