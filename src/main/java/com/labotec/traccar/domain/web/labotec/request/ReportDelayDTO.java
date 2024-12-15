package com.labotec.traccar.domain.web.labotec.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDelayDTO {

    @JsonProperty("vehicle_plate")
    @NotEmpty(message = "Vehicle plate is required")
    private String plate;  // Vehicle's plate number

    @JsonProperty("conductor_name_and_last_name")
    private String nameAndLastNameConductor;  // Name and last name of the driver

    @JsonProperty("driver_dni")
    private String dni;  // Driver's DNI (ID)

    @JsonProperty("delay_cause")
    @NotEmpty(message = "Delay cause is required")
    private String delayCause;  // Cause of the delay (e.g., "Traffic", "Mechanical Failure")

    @JsonProperty("comments")
    private String comments;  // Additional comments from the driver

    @JsonProperty("new_eta")
    @NotNull(message = "New ETA is required")
    private OffsetDateTime newETA;  // New Estimated Time of Arrival with timezone offset

    @JsonProperty("latitude")
    @DecimalMin(value = "-90.0", message = "Latitude must be between -90 and 90")
    @DecimalMax(value = "90.0", message = "Latitude must be between -90 and 90")
    private double latitude;  // Latitude of the vehicle at the time of the report

    @JsonProperty("longitude")
    @DecimalMin(value = "-180.0", message = "Longitude must be between -180 and 180")
    @DecimalMax(value = "180.0", message = "Longitude must be between -180 and 180")
    private double longitude;  // Longitude of the vehicle at the time of the report

    // Custom validation: At least one of the two fields (dni or nameAndLastNameConductor) should be non-empty
    @AssertTrue(message = "Either 'dni' or 'nameAndLastNameConductor' must be provided")
    public boolean isValidDriverInfo() {
        return dni != null && !dni.isEmpty() || nameAndLastNameConductor != null && !nameAndLastNameConductor.isEmpty();
    }
}
