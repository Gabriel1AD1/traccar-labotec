package com.labotec.traccar.domain.database.models;

import com.labotec.traccar.domain.enums.STATE;
import com.labotec.traccar.domain.enums.TYPE_GEOFENCE;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    private Long id;
    private User userId;
    private Company companyId;
    private Instant departureTime;
    private Instant arrivalTime;
    private Vehicle vehicle;
    private Location location;
    private Route route;
    private String sheetNumber;
    private STATE status;
    private Long geofenceId;
    private Long radiusValidateRoutePolyline;
    private Boolean validateRouteExplicit;
    private List<DriverSchedule> drivers;
    private TYPE_GEOFENCE geofenceType;
    private Instant estimatedDepartureTime;
    private Instant estimatedArrivalTime;
    private Instant lastModifiedDate;
    private Instant createdDate;
}
