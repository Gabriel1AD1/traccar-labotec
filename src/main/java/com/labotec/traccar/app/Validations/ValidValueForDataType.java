package com.labotec.traccar.app.Validations;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Anotación personalizada para validar el valor en función del dataType
@Constraint(validatedBy = ValueForDataTypeValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidValueForDataType {
    String message() default "Valor no válido para el tipo de dato especificado.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

