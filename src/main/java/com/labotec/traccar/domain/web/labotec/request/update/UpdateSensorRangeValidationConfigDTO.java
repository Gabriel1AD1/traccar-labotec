package com.labotec.traccar.domain.web.labotec.request.update;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.labotec.traccar.domain.embebed.RangeValue;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateSensorRangeValidationConfigDTO {
    @JsonProperty("name_validation")
    @NotNull
    private String nameValidation;

    @JsonProperty("device_id")
    @NotNull(message = "El ID del dispositivo es obligatorio.")
    private Long deviceId;

    @JsonProperty("name_sensor")
    @NotNull(message = "El nombre del sensor es obligatorio.")
    private String nameSensor;  // Nombre del sensor (puede ser 'puerta', 'temperatura', etc.)

    @JsonProperty("value")
    @NotNull(message = "El valor es obligatorio.")
    private RangeValue value;

    @JsonProperty("message_alert")
    @NotNull(message = "El mensaje de alerta es obligatorio.")
    private String messageAlert; // Mensaje de alerta
}
