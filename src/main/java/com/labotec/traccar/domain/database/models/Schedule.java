package com.labotec.traccar.domain.database.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.labotec.traccar.app.enums.RouteType;
import com.labotec.traccar.domain.enums.STATE;
import com.labotec.traccar.domain.enums.TYPE_GEOFENCE;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import lombok.*;

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
    private Long radiusValidateRoutePolyline;
    private Boolean validateRouteExplicit;
    private List<DriverSchedule> drivers;
    private TYPE_GEOFENCE geofenceType;

    private Instant estimatedDepartureTime;

    private Instant estimatedArrivalTime;

    private Instant createdDate;

    private Instant lastModifiedDate;
}
