package com.labotec.traccar.infra.db.mysql.jpa.labotec.projection;

import com.labotec.traccar.domain.enums.AlertType;

import java.time.Instant;

public interface ResponseAlertProjection {
    AlertType getAlertType(); // Tipo de alerta (enum).
    String getDescription();  // Descripción de la alerta.
    Double getLatitude();     // Latitud de la ubicación del evento.
    Double getLongitude();    // Longitud de la ubicación del evento.
    Instant getCreatedAt();   // Fecha y hora en que se creó la alerta.
}
