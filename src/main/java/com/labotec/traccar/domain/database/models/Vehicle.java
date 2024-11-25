package com.labotec.traccar.domain.database.models;

import com.labotec.traccar.domain.enums.STATE;
import lombok.*;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Vehicle {
    private User userId;
    private Company companyId;
    private Long traccarDeviceId;
    private String licensePlate;
    private STATE status;
    private String registerNumber;
    private String brand;
    private String model;
    private VehicleType typeVehicle;
    private Instant lastModifiedDate;
    private Instant createdDate;

}