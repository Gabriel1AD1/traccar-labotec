package com.labotec.traccar.app.Validations;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = SinglePrimaryPolylineValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface SinglePrimaryPolyline {
    String message() default "There must be only one primary polyline.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
