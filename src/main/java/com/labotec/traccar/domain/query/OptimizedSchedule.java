package com.labotec.traccar.domain.query;

import com.labotec.traccar.domain.enums.STATE;
import com.labotec.traccar.domain.enums.TypeGeofence;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OptimizedSchedule {
    private TypeGeofence typeGeofence;
    private Long geofenceId;
    private Long radiusValidatePolyline;
    private Boolean validateRouteExplicit;
    private STATE state;
}
