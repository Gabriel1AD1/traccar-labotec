package com.labotec.traccar.domain.query;

import com.labotec.traccar.domain.enums.STATE;
import com.labotec.traccar.domain.enums.TypeGeofence;
import lombok.Data;

@Data
public class ScheduleProcessPosition {
    private Long id;
    private ScheduleRouteProjection route;
    private TypeGeofence typeGeofence;
    private Long geofenceId;
    private Long radiusValidatePolyline;
    private Boolean validateRouteExplicit;
    private STATE state;
}
