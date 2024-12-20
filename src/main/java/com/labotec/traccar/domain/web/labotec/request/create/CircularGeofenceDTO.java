package com.labotec.traccar.domain.web.labotec.request.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CircularGeofenceDTO {

    @NotEmpty(message = "El nombre no puede estar vacío")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    private String name;

    @Size(max = 255, message = "La descripción no puede exceder los 255 caracteres")
    private String description;

    @NotNull(message = "La latitud no puede ser nula")
    @Min(value = -90, message = "La latitud mínima es -90")
    @Max(value = 90, message = "La latitud máxima es 90")
    private Double latitude;

    @NotNull(message = "La longitud no puede ser nula")
    @Min(value = -180, message = "La longitud mínima es -180")
    @Max(value = 180, message = "La longitud máxima es 180")
    private Double longitude;

    @NotNull
    @JsonProperty("radius")
    private Double radius;


}
