package com.labotec.traccar.app.Validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ConsistentBusStopsValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConsistentBusStops {
    String message() default "Bus stops are inconsistent or order is invalid.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
