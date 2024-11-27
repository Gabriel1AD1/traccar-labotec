package com.labotec.traccar.infra.db.mysql.jpa.labotec.projection;

import com.labotec.traccar.domain.enums.STATE;
import com.labotec.traccar.domain.enums.TYPE_GEOFENCE;

public interface ScheduleProjection {
    Long getId();
    RouteProjection getRoute();
    TYPE_GEOFENCE getGeofenceType();
    Long getGeofenceId();
    Long getRadiusValidateRoutePolyline();
    Boolean getValidateRouteExplicit();
    STATE getStatus();
}
