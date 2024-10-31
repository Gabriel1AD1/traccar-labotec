package com.labotec.traccar.domain.web.dto.entel.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDTO {

    @NotNull(message = "Name is required")
    @Size(max = 45, message = "Name must not exceed 45 characters")
    @JsonProperty("name")
    private String name;

    @NotNull(message = "RUC is required")
    @Size(max = 45, message = "RUC must not exceed 45 characters")
    @JsonProperty("ruc")
    private String ruc;

    @NotNull(message = "Status is required")
    @JsonProperty("status")
    private Byte status;
}
