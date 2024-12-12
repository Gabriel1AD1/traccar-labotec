package com.labotec.traccar.domain.web.dto.labotec.response;

import com.labotec.traccar.domain.enums.AlertType;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ResponseAlert {
    private AlertType alertType; // Tipo de alerta (enum).

    private String description; // Descripción de la alerta.

    private Double latitude; // Latitud de la ubicación del evento.

    private Double longitude; // Longitud de la ubicación del evento.

    private Instant createdAt; // Fecha y hora en que se creó la alerta.
}
