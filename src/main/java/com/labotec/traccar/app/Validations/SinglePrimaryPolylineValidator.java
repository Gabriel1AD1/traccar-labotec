package com.labotec.traccar.app.Validations;

import com.labotec.traccar.domain.web.dto.entel.create.RouteDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

public class SinglePrimaryPolylineValidator implements ConstraintValidator<SinglePrimaryPolyline, List<RouteDTO.BusStopDTO.PolylineDTO>> {

    @Override
    public boolean isValid(List<RouteDTO.BusStopDTO.PolylineDTO> polylineList, ConstraintValidatorContext context) {
        if (polylineList == null || polylineList.isEmpty()) {
            return true; // Permitir nulo o lista vacía
        }

        long primaryCount = polylineList.stream()
                .filter(RouteDTO.BusStopDTO.PolylineDTO::getIsPrimary)
                .count();

        // Si hay más de una polilínea primaria, se construye un mensaje de error
        if (primaryCount > 1) {
            context.disableDefaultConstraintViolation(); // Desactivar el mensaje de error predeterminado
            context.buildConstraintViolationWithTemplate("Only one polyline can be marked as primary.")
                    .addConstraintViolation(); // Añadir el nuevo mensaje de error
            return false; // La validación falla
        }

        return true; // La validación es exitosa si hay 0 o 1 polilínea primaria
    }
}
