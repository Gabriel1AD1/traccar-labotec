package com.labotec.traccar.domain.query;

import lombok.Data;

@Data
public class ScheduleOverviewPolylineProjection {
    private Long id; // Identificador de la polilínea
    private String polyline; // La cadena que representa la polilínea
    private Boolean isPrimary; // Indica si la polilínea es primaria
}
