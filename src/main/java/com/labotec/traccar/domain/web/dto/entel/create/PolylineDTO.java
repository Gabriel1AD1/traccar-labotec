package com.labotec.traccar.domain.web.dto.entel.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PolylineDTO {
    @NotNull(message = "Polyline string is required")
    @NotEmpty// Validación para el poliline
    @JsonProperty("polyline")
    private String polyline;

    @JsonProperty("is_primary") // Para identificar la polilínea principal
    private Boolean isPrimary;
}
