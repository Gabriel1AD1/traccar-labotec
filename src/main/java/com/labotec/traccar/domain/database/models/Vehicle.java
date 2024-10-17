package com.labotec.traccar.domain.database.models;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Vehicle {
    private Integer id;
    private String licensePlate;
    private Byte status;
    private String registerNumber;
    private String brand;
    private String model;
    private Company company;
}