package com.labotec.traccar.domain.database.models;

import com.labotec.traccar.domain.database.models.optimized.TypeValidation;
import com.labotec.traccar.domain.enums.DataType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SensorValidationConfig {
    private Long id;
    private Long companyId;
    private Long userId;
    private Long deviceId;
    private Boolean state;
    private String nameValidation;
    private String nameSensor;  // Nombre del sensor (puede ser 'puerta', 'temperatura', etc.)
    private String operator;    // Operador para comparar el valor (por ejemplo, '=', '>', '<', '>=', etc.)
    private String value;
    private TypeValidation typeValidation;
    private String messageAlert;
    private DataType dataType;
}
