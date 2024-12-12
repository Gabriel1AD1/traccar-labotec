package com.labotec.traccar.infra.db.mysql.jpa.labotec.projection;

import com.labotec.traccar.domain.enums.STATE;
import com.labotec.traccar.domain.enums.TypeGeofence;

public interface OptimizedScheduleProjection {

    // Campos de la proyección
    TypeGeofence getGeofenceType();
    Long getGeofenceId();
    Long getRadiusValidateRoutePolyline();
    Boolean getValidateRouteExplicit();
    STATE getStatus();
}
