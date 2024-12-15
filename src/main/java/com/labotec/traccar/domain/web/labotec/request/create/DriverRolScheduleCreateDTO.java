package com.labotec.traccar.domain.web.labotec.request.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.RouteAssignmentRole;
import lombok.Data;

@Data
public class DriverRolScheduleCreateDTO {
    @JsonProperty("driver_id")
    private Long driverId;
    @JsonProperty("route_assigment_rol")
    private RouteAssignmentRole routeAssignmentRole;
}
