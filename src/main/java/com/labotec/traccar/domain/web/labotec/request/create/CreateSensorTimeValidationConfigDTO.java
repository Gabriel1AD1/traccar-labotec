package com.labotec.traccar.domain.web.labotec.request.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.labotec.traccar.domain.embebed.ValidatorTime;
import com.labotec.traccar.domain.enums.DataType;
import com.labotec.traccar.domain.database.models.TypeSensor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateSensorTimeValidationConfigDTO {
    @JsonProperty("name_validation")
    @NotNull
    private String nameValidation;
    @JsonProperty("device_id")  // Nombre del campo JSON
    private Long deviceId;
    @JsonProperty("sensor_id") @NotNull(message = "El id del sensor es obligatorio.")
    private Long sensorId;  // Nombre del sensor (puede ser 'puerta', 'temperatura', etc.)
    @JsonProperty("operator") @NotNull(message = "El operador no puede ser nulo")
    @Pattern(regexp = "^(=|!=|<|>|<=|>=)$", message = "Operador inválido. Los operadores permitidos son: '=', '!=', '<', '>', '<=', '>='")
    private String operator;    // Operador para comparar el valor (por ejemplo, '=', '>', '<', '>=', etc.)
    @JsonProperty("validator_time")
    private ValidatorTime validatorTime;
    @JsonProperty("message_alert")
    private String messageAlert;

}
