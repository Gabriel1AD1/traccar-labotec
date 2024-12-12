package com.labotec.traccar.domain.database.models;

import com.labotec.traccar.domain.enums.AlertType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Representa un objeto de dominio para manejar alertas en la capa de negocio.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Alert {
    private Long id; // Identificador único de la alerta.

    private AlertType alertType; // Tipo de alerta (enum).

    private String description; // Descripción de la alerta.

    private Long vehicleId; // Identificador del vehículo asociado.

    private Long userId; // Identificador del usuario asociado.

    private Double latitude; // Latitud de la ubicación del evento.

    private Double longitude; // Longitud de la ubicación del evento.

    private Instant createdAt; // Fecha y hora en que se creó la alerta.
}
