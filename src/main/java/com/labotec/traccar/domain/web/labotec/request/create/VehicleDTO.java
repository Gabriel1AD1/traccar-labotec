package com.labotec.traccar.domain.web.labotec.request.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.labotec.traccar.domain.enums.STATE;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleDTO {

    @NotNull(message = "License plate is required")
    @Size(max = 50, message = "License plate must not exceed 50 characters")
    @JsonProperty("license_plate")  // Mapea con "license_plate" en el JSON
    private String licensePlate;

    @NotNull(message = "Status is required")
    @JsonProperty("status")  // Mapea con "status" en el JSON
    private STATE status;

    @NotNull(message = "Register number is required")
    @Size(max = 50, message = "Register number must not exceed 50 characters")
    @JsonProperty("register_number")  // Mapea con "register_number" en el JSON
    private String registerNumber;

    @NotNull(message = "Brand is required")
    @Size(max = 50, message = "Brand must not exceed 50 characters")
    @JsonProperty("brand")  // Mapea con "brand" en el JSON
    private String brand;

    @NotNull(message = "Model is required")
    @Size(max = 50, message = "Model must not exceed 50 characters")
    @JsonProperty("model")  // Mapea con "model" en el JSON
    private String model;

    @JsonProperty("imei")
    @NotNull
    private String imei;

    @JsonProperty("name_device")
    @NotNull
    private String nameDevice;

    @JsonProperty("type_vehicle_id")  // Mapea con "company_id" en el JSON
    private Long typeVehicleId;  // Refers to the company id
}
