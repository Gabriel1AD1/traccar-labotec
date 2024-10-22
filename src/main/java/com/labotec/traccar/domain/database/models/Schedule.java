package com.labotec.traccar.domain.database.models;

import lombok.*;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Schedule {
    private Integer id;
    private Instant departureTime;
    private Instant arrivalTime;
    private Vehicle vehicle;
    private Driver driver;
    private Location location;
    private Route route;
    private String sheetNumber;
    private Byte status;
    private Instant estimatedDepartureTime;
    private Instant estimatedArrivalTime;
    private Company company;
    private Instant lastModifiedDate;
    private Instant createdDate;
}
