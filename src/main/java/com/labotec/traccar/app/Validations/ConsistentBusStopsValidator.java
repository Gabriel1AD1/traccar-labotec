package com.labotec.traccar.app.Validations;

import com.labotec.traccar.domain.web.dto.labotec.request.create.RouteBusStopCreateDTO;
import com.labotec.traccar.domain.web.dto.labotec.request.create.RouteCreateDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class ConsistentBusStopsValidator implements ConstraintValidator<ConsistentBusStops, RouteCreateDTO> {

    @Override
    public boolean isValid(RouteCreateDTO route, ConstraintValidatorContext context) {
        List<RouteBusStopCreateDTO> segments = route.getSegments();

        if (segments == null || segments.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("The list of bus stops cannot be null or empty.")
                    .addConstraintViolation();
            return false;
        }

        for (int i = 0; i < segments.size() - 1; i++) {
            RouteBusStopCreateDTO current = segments.get(i);
            RouteBusStopCreateDTO next = segments.get(i + 1);

            // Validar que el end_bus_stop_id del actual coincida con el start_bus_stop_id del siguiente
            if (!current.getEndBusStopId().equals(next.getStartBusStopId())) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                                String.format("Bus stops are inconsistent at order %d and %d.", current.getOrder(), next.getOrder()))
                        .addConstraintViolation();
                return false;
            }

            // Validar que el orden sea consecutivo
            if (!next.getOrder().equals(current.getOrder() + 1)) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                                String.format("Order is not consecutive at %d.", current.getOrder()))
                        .addConstraintViolation();
                return false;
            }
        }

        return true; // Validación exitosa
    }
}
