package com.labotec.traccar.domain.web.labotec.request.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateSensorExistValidationConfigDTO {
    @JsonProperty("name_validation")
    @NotNull
    private String nameValidation;

    @JsonProperty("device_id")
    @NotNull(message = "El ID del dispositivo es obligatorio.")
    private Long deviceId;

    @JsonProperty("sensor_id")
    @NotNull(message = "El id del sensor es obligatorio.")
    private Long sensorId;  // Nombre del sensor (puede ser 'puerta', 'temperatura', etc.)

    @JsonProperty("message_alert")
    @NotNull(message = "El mensaje de alerta es obligatorio.")
    private String messageAlert; // Mensaje de alerta

}
