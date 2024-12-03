package com.labotec.traccar.domain.query;

import com.labotec.traccar.domain.enums.STATE;
import lombok.Data;

@Data
public class ScheduleBusStopProjection {
    private Long id; // Identificador del paradero
    private String name; // Nombre del paradero
    private String description; // Descripción del paradero
    private Double latitude; // Latitud del paradero
    private Double longitude; // Longitud del paradero
    private STATE status; // Estado del paradero
}
