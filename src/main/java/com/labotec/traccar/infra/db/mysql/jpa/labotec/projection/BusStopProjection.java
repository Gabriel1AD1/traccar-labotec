package com.labotec.traccar.infra.db.mysql.jpa.labotec.projection;


import com.labotec.traccar.domain.enums.STATE;

public interface BusStopProjection {
    Long getId();                // id del paradero
    String getName();            // nombre del paradero
    String getDescription();     // descripción del paradero
    String getLatitude();        // latitud del paradero
    String getLongitude();       // longitud del paradero
    STATE getStatus();           // estado del paradero
}
