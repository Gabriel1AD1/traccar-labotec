package com.labotec.traccar.infra.db.mysql.jpa.labotec.projection;

public interface ResponseCircularGeofenceProjection {
    String getName();
    Double getLatitude();
    Double getLongitude();
    Double getRadius();
}
