package com.labotec.traccar.domain.web.labotec.response;

import com.labotec.traccar.domain.enums.DataType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseSensorValidatorConfig {
    private String nameSensor;  // Nombre del sensor (puede ser 'puerta', 'temperatura', etc.)
    private String operator;    // Operador para comparar el valor (por ejemplo, '=', '>', '<', '>=', etc.)
    private String value;
    private String messageAlert;
    private DataType dataType;
}
