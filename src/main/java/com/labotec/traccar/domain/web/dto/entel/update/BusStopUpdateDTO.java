package com.labotec.traccar.domain.web.dto.entel.update;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BusStopUpdateDTO {
    @NotEmpty(message = "Name is required")
    @Size(max = 200, message = "Name must not exceed 200 characters")
    @JsonProperty("name")
    private String name;

    @Size(max = 100, message = "Latitude must not exceed 100 characters")
    @NotEmpty(message = "latitude is required")
    @JsonProperty("latitude")
    private String latitude;

    @Size(max = 100, message = "Longitude must not exceed 100 characters")
    @NotEmpty(message = "longitude is required")
    @JsonProperty("longitude")
    private String longitude;

    @NotNull(message = "Status is required")
    @JsonProperty("status")
    private Byte status;

    @NotNull(message = "Company is required")
    @JsonProperty("company_id")
    private Integer companyId;
}
