package com.labotec.traccar.domain.database.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.labotec.traccar.domain.enums.STATE;
import com.labotec.traccar.domain.enums.TypeGeofence;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    private Long id;
    @JsonIgnore
    private User userId;
    @JsonIgnore
    private Company companyId;
    private Instant departureTime;
    private Instant arrivalTime;
    private Vehicle vehicle;
    private Location location;
    private Route route;
    @DecimalMax(value = "100.00", message = "El porcentaje no puede exceder el 100.00")
    @DecimalMin(value = "0.0" , message = "El porcentaje minimo es 0.0")
    private Double percentageTraveled;
    private String sheetNumber;
    private STATE status;
    private Long geofenceId;
    private Boolean isProgramCompleted;
    private Long radiusValidateRoutePolyline;
    private Boolean validateRouteExplicit;
    private List<DriverSchedule> drivers;
    private TypeGeofence geofenceType;

    private Instant estimatedDepartureTime;

    private Instant estimatedArrivalTime;

    private Instant createdDate;

    private Instant lastModifiedDate;
}
