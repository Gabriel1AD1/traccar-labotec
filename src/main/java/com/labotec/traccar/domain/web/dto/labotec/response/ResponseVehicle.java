package com.labotec.traccar.domain.web.dto.labotec.response;

import com.labotec.traccar.domain.enums.STATE;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ResponseVehicle {
    private String licensePlate;
    private String typeVehicleName;
    private STATE status;
    private String registerNumber;
    private String brand;
    private String model;
}
