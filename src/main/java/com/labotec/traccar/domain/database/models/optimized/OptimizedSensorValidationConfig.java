package com.labotec.traccar.domain.database.models.optimized;


import com.labotec.traccar.app.utils.JsonUtils;
import com.labotec.traccar.domain.embebed.ValidatorTime;
import com.labotec.traccar.domain.enums.DataType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OptimizedSensorValidationConfig {
    private String nameSensor;  // Nombre del sensor (puede ser 'puerta', 'temperatura', etc.)
    private String operator;    // Operador para comparar el valor (por ejemplo, '=', '>', '<', '>=', etc.)
    private String value;
    private String messageError;
    private TypeValidation typeValidation;
    private DataType dataType;
    // funcion para convertir el objeto ValidatorTime a JSON y asignarlo a 'value'
    public void setValidatorTime(ValidatorTime validatorTime) {
        // Usar el JsonUtils para convertir el objeto ValidatorTime a un JSON String
        this.value = JsonUtils.toJson(validatorTime);
    }

    // funcion para obtener el objeto ValidatorTime a partir del JSON almacenado en 'value'
    public ValidatorTime getValidatorTime() {
            // Convertir el JSON de 'value' de nuevo a un objeto ValidatorTime
            return JsonUtils.toObject(this.value, ValidatorTime.class);
    }
}
