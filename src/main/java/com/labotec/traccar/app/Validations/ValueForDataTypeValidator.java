package com.labotec.traccar.app.Validations;

import com.labotec.traccar.domain.enums.DataType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.Setter;

@Setter
public class ValueForDataTypeValidator implements ConstraintValidator<ValidValueForDataType, String> {

    private DataType dataType;

    @Override
    public void initialize(ValidValueForDataType constraintAnnotation) {
        // No necesitamos inicializar nada en este caso
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Si no hay valor, no lo validamos (esto dependerá de tu lógica)
        if (value == null || value.isEmpty()) {
            return true;
        }

        // Validamos según el dataType
        if (dataType != null) {
            switch (dataType) {
                case INT:
                    try {
                        Integer.parseInt(value);  // Verifica si es un número entero
                        return true;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                case DOUBLE:
                    try {
                        Double.parseDouble(value);  // Verifica si es un número decimal
                        return true;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                case TEXT:
                    // Si es TEXT, cualquier valor es válido
                    return true;
                default:
                    return false;
            }
        }
        return false;
    }

}
