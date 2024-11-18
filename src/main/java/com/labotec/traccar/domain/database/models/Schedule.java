package com.labotec.traccar.domain.database.models;

import com.labotec.traccar.domain.enums.STATE;
import lombok.*;

import java.time.Instant;

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
    private Driver driver;
    private Location location;
    private Route route;
    private String sheetNumber;
    private STATE status;
    private CircularGeofence geofence;
    private Instant estimatedDepartureTime;
    private Instant estimatedArrivalTime;
    private Instant lastModifiedDate;
    private Instant createdDate;
}
