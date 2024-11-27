package com.labotec.traccar.infra.db.mysql.jpa.labotec.projection;

public interface OverviewPolylineProjection {
    Long getId();
    String getPolyline();
    Boolean getIsPrimary();
}