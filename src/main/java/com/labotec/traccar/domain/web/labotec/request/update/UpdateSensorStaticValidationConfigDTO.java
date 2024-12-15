package com.labotec.traccar.domain.web.labotec.request.update;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.labotec.traccar.app.Validations.ValidValueForDataType;
import com.labotec.traccar.domain.enums.DataType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateSensorStaticValidationConfigDTO {

    @JsonProperty("sensor_name")
    @NotNull
    private String nameSensor;  // Nombre del sensor (puede ser 'puerta', 'temperatura', etc.)
    @JsonProperty("name_validation")
    @NotNull
    private String nameValidation;
    @JsonProperty("operator")
    @NotNull(message = "El operador no puede ser nulo")
    @Pattern(regexp = "^(=|!=|<|>|<=|>=)$", message = "Operador inválido. Los operadores permitidos son: '=', '!=', '<', '>', '<=', '>='")
    private String operator;    // Operador para comparar el valor (por ejemplo, '=', '>', '<', '>=', etc.)

    @JsonProperty("state")
    @NotNull
    private Boolean state;

    @JsonProperty("value")
    @NotNull(message = "El valor no puede ser nulo")
    @Size(min = 1, message = "El valor debe tener al menos 1 carácter")
    @ValidValueForDataType(message = "El valor no es válido para el tipo de dato especificado.")
    private String value;

    @JsonProperty("message_alert")
    @NotNull
    private String messageAlert;

    @JsonProperty("data_type")
    @NotNull(message = "El tipo de dato no puede ser nulo")
    private DataType dataType; // Tipo de dato (INT, DOUBLE, TEXT)

}
