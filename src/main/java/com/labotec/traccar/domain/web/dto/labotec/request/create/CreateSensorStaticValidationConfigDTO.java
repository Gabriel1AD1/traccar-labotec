package com.labotec.traccar.domain.web.dto.labotec.request.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.labotec.traccar.app.Validations.ValidValueForDataType;
import com.labotec.traccar.domain.database.models.optimized.TypeValidation;
import com.labotec.traccar.domain.enums.DataType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateSensorStaticValidationConfigDTO {
    @JsonProperty("name_validation")
    @NotNull
    private String nameValidation;


    @JsonProperty("device_id")
    @NotNull(message = "El ID del dispositivo es obligatorio.")
    private Long deviceId;

    @JsonProperty("name_sensor")
    @NotNull(message = "El nombre del sensor es obligatorio.")
    private String nameSensor;  // Nombre del sensor (puede ser 'puerta', 'temperatura', etc.)

    @JsonProperty("operator")
    @NotNull(message = "El operador es obligatorio.")
    private String operator;    // Operador para comparar el valor (por ejemplo, '=', '>', '<', '>=', etc.)

    @JsonProperty("value")
    @NotNull(message = "El valor es obligatorio.")
    @ValidValueForDataType(message = "El valor no es compatible con el tipo de dato.")
    private String value;       // El valor que será comparado, dependiendo del tipo de dato

    @JsonProperty("message_alert")
    @NotNull(message = "El mensaje de alerta es obligatorio.")
    private String messageAlert; // Mensaje de alerta

    @JsonProperty("data_type")
    @NotNull(message = "El tipo de dato es obligatorio.")
    private DataType dataType;  // Tipo de dato (INT, DOUBLE, TEXT)
}
