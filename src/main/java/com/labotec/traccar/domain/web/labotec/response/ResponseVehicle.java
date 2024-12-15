package com.labotec.traccar.domain.web.labotec.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.labotec.traccar.domain.enums.STATE;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ResponseVehicle {

    @JsonProperty("license_plate")
    private String licensePlate;

    @JsonProperty("type_vehicle_name")
    private String typeVehicleName;

    @JsonProperty("status")
    private STATE status;

    @JsonProperty("register_number")
    private String registerNumber;

    @JsonProperty("brand")
    private String brand;

    @JsonProperty("model")
    private String model;
}
