package com.labotec.traccar.domain.web.dto.labotec.request.create;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.labotec.traccar.domain.enums.TypeGeofence;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class ScheduleDTO {

    @NotNull(message = "Vehicle is required")
    @JsonProperty("vehicle_id")
    private Long vehicleId;

    @JsonProperty("drivers_ids")
    private List<DriverRolScheduleCreateDTO> driverAssignmentRoute;

    @NotNull(message = "Location ID is required")
    @JsonProperty("location_id")
    private Long locationId;

    @JsonProperty("type_geofence")
    @NotNull
    private TypeGeofence geofenceType;

    @NotNull
    @JsonProperty("geofence_id")
    private Long geofenceId;

    @NotNull(message = "Route is required")
    @JsonProperty("route_id")
    private Long routeId;

    @JsonProperty("sheet_number")
    private String sheetNumber;

    @JsonProperty(value = "validate_route_explicit", defaultValue = "false")
    private Boolean validateRouteExplicit;

    @JsonProperty(value = "radius_validate_route", defaultValue = "0")
    private Long radiusValidateRoutePolyline;

    @NotNull(message = "Estimated departure time is required")
    @JsonProperty("estimated_departure_time")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private ZonedDateTime zoneEstimatedDepartureTime;

    @NotNull(message = "Estimated arrival time is required")
    @JsonProperty("estimated_arrival_time")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private ZonedDateTime zoneEstimatedArrivalTime;

    // Validación personalizada usando @AssertTrue
    @AssertTrue(message = "Estimated arrival time must be after the estimated departure time")
    public boolean isEstimatedArrivalTimeValid() {
        if (zoneEstimatedDepartureTime != null && zoneEstimatedArrivalTime != null) {
            return !zoneEstimatedArrivalTime.isBefore(zoneEstimatedDepartureTime);
        }
        return true;  // Si alguno es nulo, la validación pasa (puedes modificar esto si lo prefieres)
    }
}
