package com.labotec.traccar.domain.database.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.labotec.traccar.domain.enums.STATE;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Vehicle {
    private Long deviceId;
    @JsonIgnore
    private User userId;
    @JsonIgnore
    private Company companyId;
    private String licensePlate;
    private STATE status;
    private String registerNumber;
    private String brand;
    private String model;
    private VehicleType typeVehicle;
    @JsonIgnore
    private Instant lastModifiedDate;
    @JsonIgnore
    private Instant createdDate;

    public Vehicle(Long deviceId) {
        this.setDeviceId(deviceId);
    }
}