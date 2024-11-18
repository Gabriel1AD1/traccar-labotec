package com.labotec.traccar.domain.web.dto.entel.update;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyUpdateDTO {

    @NotNull(message = "RUC is required")
    @Size(max = 45, message = "RUC must not exceed 45 characters")
    @JsonProperty("ruc")
    private String ruc;

}
