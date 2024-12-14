package com.labotec.traccar.domain.web.dto.labotec.request.update;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.labotec.traccar.domain.database.models.optimized.TypeValidation;
import com.labotec.traccar.domain.embebed.ValidatorTime;
import com.labotec.traccar.domain.enums.DataType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data@Builder
public class UpdateSensorTimeValidationConfigDTO {
    @JsonProperty("sensor_name")
    @NotNull
    private String nameSensor;  // Nombre del sensor (puede ser 'puerta', 'temperatura', etc.)
    @JsonProperty("name_validation")
    @NotNull
    private String nameValidation;
    @JsonProperty("state")
    @NotNull
    private Boolean state;
    @JsonProperty("operator")
    @NotNull(message = "El operador no puede ser nulo")
    @Pattern(regexp = "^(=|!=|<|>|<=|>=)$", message = "Operador inválido. Los operadores permitidos son: '=', '!=', '<', '>', '<=', '>='")
    private String operator;    // Operador para comparar el valor (por ejemplo, '=', '>', '<', '>=', etc.)

    @JsonProperty("validator_time")
    @NotNull
    private ValidatorTime validatorTime;

    @JsonProperty("message_alert")
    @NotNull
    private String messageAlert;

    @JsonProperty("data_type")
    @NotNull(message = "El tipo de dato no puede ser nulo")
    private DataType dataType; // Tipo de dato (INT, DOUBLE, TEXT)
}
