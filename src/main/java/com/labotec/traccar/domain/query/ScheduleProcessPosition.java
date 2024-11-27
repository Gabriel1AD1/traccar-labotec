package com.labotec.traccar.domain.query;

import com.labotec.traccar.domain.enums.STATE;
import com.labotec.traccar.domain.enums.TYPE_GEOFENCE;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.projection.ScheduleProjection;
import lombok.Data;

import java.util.List;

@Data
public class ScheduleProcessPosition {
    private Long id;
    private ScheduleRouteProjection route;
    private TYPE_GEOFENCE typeGeofence;
    private Long geofenceId;
    private Long radiusValidatePolyline;
    private Boolean validateRouteExplicit;
    private STATE state;

}
