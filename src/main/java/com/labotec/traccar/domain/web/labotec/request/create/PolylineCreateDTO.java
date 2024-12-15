package com.labotec.traccar.domain.web.labotec.request.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PolylineCreateDTO {
    @NotNull(message = "Polyline string is required")
    @NotEmpty
    @JsonProperty("polyline")
    private String polyline;

    @JsonProperty("is_primary")
    private Boolean isPrimary;
}
