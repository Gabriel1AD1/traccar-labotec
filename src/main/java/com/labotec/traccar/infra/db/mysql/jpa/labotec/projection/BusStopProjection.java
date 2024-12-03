package com.labotec.traccar.infra.db.mysql.jpa.labotec.projection;


import com.labotec.traccar.domain.enums.STATE;

public interface BusStopProjection {
    Long getId();                // id del paradero
    String getName();            // nombre del paradero
    String getDescription();     // descripción del paradero
    Double getLatitude();        // latitud del paradero
    Double getLongitude();       // longitud del paradero
    STATE getStatus();           // estado del paradero
}
