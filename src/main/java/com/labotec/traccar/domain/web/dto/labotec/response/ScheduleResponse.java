package com.labotec.traccar.domain.web.dto.labotec.response;

import com.labotec.traccar.domain.database.models.DriverSchedule;
import com.labotec.traccar.domain.database.models.Location;
import com.labotec.traccar.domain.database.models.Route;
import com.labotec.traccar.domain.database.models.Vehicle;
import com.labotec.traccar.domain.enums.STATE;
import com.labotec.traccar.domain.enums.TYPE_GEOFENCE;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class ScheduleResponse{
    private Instant departureTime;
    private Instant arrivalTime;
    private Vehicle vehicle;
    private Location location;
    private Route route;
    @DecimalMax(value = "100.00", message = "El porcentaje no puede exceder el 100.00")
    @DecimalMin(value = "0.0" , message = "El porcentaje minimo es 0.0")
    private Double percentageTraveled;
    private String sheetNumber;
    private STATE status;
    private Long geofenceId;
    private Long radiusValidateRoutePolyline;
    private Boolean validateRouteExplicit;
    private List<DriverScheduleResponse> drivers;
    private TYPE_GEOFENCE geofenceType;
}
