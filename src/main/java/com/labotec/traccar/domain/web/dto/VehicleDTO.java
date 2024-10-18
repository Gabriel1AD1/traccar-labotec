package com.labotec.traccar.domain.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleDTO {

    @NotNull(message = "License plate is required")
    @Size(max = 50, message = "License plate must not exceed 50 characters")
    private String licensePlate;

    @NotNull(message = "Status is required")
    private Byte status;

    @NotNull(message = "Register number is required")
    @Size(max = 50, message = "Register number must not exceed 50 characters")
    private String registerNumber;

    @NotNull(message = "Brand is required")
    @Size(max = 50, message = "Brand must not exceed 50 characters")
    private String brand;

    @NotNull(message = "Model is required")
    @Size(max = 50, message = "Model must not exceed 50 characters")
    private String model;

    @NotNull(message = "Company is required")
    private Integer companyId;  // Refers to the company id
}