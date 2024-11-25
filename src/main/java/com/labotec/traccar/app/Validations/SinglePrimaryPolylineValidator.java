package com.labotec.traccar.app.Validations;
import com.labotec.traccar.domain.web.dto.labotec.request.create.RouteBusStopCreateDTO;
import com.labotec.traccar.domain.web.dto.labotec.request.create.RouteCreateDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SinglePrimaryPolylineValidator implements ConstraintValidator<SinglePrimaryPolyline, RouteCreateDTO> {

    @Override
    public boolean isValid(RouteCreateDTO value, ConstraintValidatorContext context) {

        System.out.println("Validating RouteCreateDTO: " + value);

        if (value == null || value.getSegments() == null || value.getSegments().isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Segments list cannot be null or empty.")
                    .addConstraintViolation();
            return false;
        }

        for (RouteBusStopCreateDTO segment : value.getSegments()) {
            if (segment == null || segment.getPolylines() == null || segment.getPolylines().isEmpty()) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Each segment must have a non-empty polylines list.")
                        .addConstraintViolation();
                return false;
            }

            // Contar cuántas polylines tienen `isPrimary=true`
            long primaryCount = segment.getPolylines().stream()
                    .filter(polyline -> polyline != null && Boolean.TRUE.equals(polyline.getIsPrimary()))
                    .count();

            if (primaryCount > 1) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Segment with order " + segment.getOrder() + " has multiple primary polylines.")
                        .addConstraintViolation();
                return false; // Fallar si hay más de una primaria en un segmento
            }

            if (primaryCount == 0) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("Segment with order " + segment.getOrder() + " must have at least one primary polyline.")
                        .addConstraintViolation();
                return false; // Fallar si no hay ninguna primaria
            }
        }

        return true; // Validación exitosa si no hay errores
    }
}
