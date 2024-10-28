package com.labotec.traccar.domain.web.dto.update;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PointUpdateDTO {
    @JsonProperty("id")
    @NotNull(message = "La latitud no puede ser nula")
    private Integer id;
    @NotNull(message = "La latitud no puede ser nula")
    @Min(value = -90, message = "La latitud mínima es -90")
    @Max(value = 90, message = "La latitud máxima es 90")
    private Double latitude;

    @NotNull(message = "La longitud no puede ser nula")
    @Min(value = -180, message = "La longitud mínima es -180")
    @Max(value = 180, message = "La longitud máxima es 180")
    private Double longitude;

    @NotNull(message = "El número de orden no puede ser nulo")
    @Min(value = 1, message = "El número de orden debe ser al menos 1")
    private Integer pointOrder;
}
