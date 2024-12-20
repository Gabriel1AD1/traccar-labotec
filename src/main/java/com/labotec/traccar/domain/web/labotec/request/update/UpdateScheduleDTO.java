package com.labotec.traccar.domain.web.labotec.request.update;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.labotec.traccar.domain.enums.STATE;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.ZonedDateTime;

@Getter
@Setter
public class UpdateScheduleDTO {

    @NotNull(message = "Departure time is required")
    @JsonProperty("departure_time")  // Mapea con "departure_time" en el JSON
    private Instant departureTime;

    @NotNull(message = "Arrival time is required")
    @JsonProperty("arrival_time")  // Mapea con "arrival_time" en el JSON
    private Instant arrivalTime;

    @NotNull(message = "Vehicle is required")
    @JsonProperty("vehicle_id")  // Mapea con "vehicle_id" en el JSON
    private Long vehicleId;  // Refers to the vehicle id

    @NotNull(message = "Driver is required")
    @JsonProperty("driver_id")  // Mapea con "driver_id" en el JSON
    private Long driverId;  // Refers to the driver id

    @NotNull(message = "Location ID is required")
    @JsonProperty("location_id")  // Mapea con "location_id" en el JSON
    private Long locationId;

    @NotNull(message = "Route is required")
    @JsonProperty("route_id")  // Mapea con "route_id" en el JSON
    private Long routeId;  // Refers to the route id

    @JsonProperty("sheet_number")  // Mapea con "sheet_number" en el JSON
    private String sheetNumber;

    @NotNull(message = "Status is required")
    @JsonProperty("status")  // Mapea con "status" en el JSON
    private STATE status;

    @JsonProperty("geofence_poligonal_id")  // Mapea con "geofence_poligonal_id" en el JSON
    private Long geofencePoligonalId;

    @NotNull(message = "Estimated departure time is required")
    @JsonProperty("estimated_departure_time")  // Mapea con "estimated_departure_time" en el JSON
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private ZonedDateTime zoneEstimatedDepartureTime;

    @NotNull(message = "Estimated arrival time is required")
    @JsonProperty("estimated_arrival_time")  // Mapea con "estimated_arrival_time" en el JSON
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private ZonedDateTime zoneEstimatedArrivalTime;

}
