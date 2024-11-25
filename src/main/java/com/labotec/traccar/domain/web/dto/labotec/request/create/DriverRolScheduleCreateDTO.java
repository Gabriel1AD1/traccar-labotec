package com.labotec.traccar.domain.web.dto.labotec.request.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.ROUTE_ASSIGNMENT_ROLE;
import lombok.Data;

@Data
public class DriverRolScheduleCreateDTO {
    @JsonProperty("driver_id")
    private Long driverId;
    @JsonProperty("route_assigment_rol")
    private ROUTE_ASSIGNMENT_ROLE routeAssignmentRole;
}
