package com.labotec.traccar.domain.database.models;

import lombok.*;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Vehicle {
    private Integer id;
    private Integer traccarDeviceId;
    private String licensePlate;
    private Byte status;
    private String registerNumber;
    private String brand;
    private String model;
    private Company company;
    private VehicleType typeVehicle;
    private Instant lastModifiedDate;
    private Instant createdDate;

}