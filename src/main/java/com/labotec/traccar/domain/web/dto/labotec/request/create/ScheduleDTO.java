package com.labotec.traccar.domain.web.dto.labotec.request.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.labotec.traccar.domain.enums.STATE;
import com.labotec.traccar.domain.enums.TYPE_GEOFENCE;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class ScheduleDTO {

    @NotNull(message = "Departure time is required")
    @JsonProperty("departure_time")  // Mapea con "departure_time" en el JSON
    private Instant departureTime;
    @NotNull(message = "Arrival time is required")
    @JsonProperty("arrival_time")  // Mapea con "arrival_time" en el JSON
    private Instant arrivalTime;
    @NotNull(message = "Vehicle is required")
    @JsonProperty("vehicle_id")  // Mapea con "vehicle_id" en el JSON
    private Long vehicleId;  // Refers to the vehicle id
    @JsonProperty("drivers_ids")
    private List<DriverRolScheduleCreateDTO> driverAssignmentRoute;
    @NotNull(message = "Location ID is required")
    @JsonProperty("location_id")  // Mapea con "location_id" en el JSON
    private Long locationId;
    @JsonProperty("type_geofence")
    @NotNull
    private TYPE_GEOFENCE geofenceType;

    @NotNull
    @JsonProperty("geofence_id")
    private Long geofenceId;

    @NotNull(message = "Route is required")
    @JsonProperty("route_id")  // Mapea con "route_id" en el JSON
    private Long routeId;  // Refers to the route id

    @JsonProperty("sheet_number")  // Mapea con "sheet_number" en el JSON
    private String sheetNumber;

    @JsonProperty(value = "validate_route_explicit",defaultValue = "false")
    private Boolean validateRouteExplicit;

    @JsonProperty(value = "radius_validate_route",defaultValue = "0")
    private Long radiusValidateRoutePolyline;
    @NotNull(message = "Estimated departure time is required")
    @JsonProperty("estimated_departure_time")  // Mapea con "estimated_departure_time" en el JSON
    private Instant estimatedDepartureTime;

    @NotNull(message = "Estimated arrival time is required")
    @JsonProperty("estimated_arrival_time")  // Mapea con "estimated_arrival_time" en el JSON
    private Instant estimatedArrivalTime;
}
