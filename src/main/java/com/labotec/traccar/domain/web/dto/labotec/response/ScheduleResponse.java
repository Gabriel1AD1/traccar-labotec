package com.labotec.traccar.domain.web.dto.labotec.response;

import com.labotec.traccar.domain.enums.STATE;
import com.labotec.traccar.domain.enums.TypeGeofence;
import lombok.Data;

@Data
public class ScheduleResponse{
    private Double percentageTraveled;
    private String sheetNumber;
    private STATE status;
    private Long geofenceId;
    private Long radiusValidateRoutePolyline;
    private Boolean validateRouteExplicit;
    private TypeGeofence geofenceType;
}
