package com.labotec.traccar.domain.web.labotec.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.RouteAssignmentRole;
import lombok.Data;

@Data
public class DriverScheduleResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("schedule_id")
    private Long scheduleId;

    @JsonProperty("driver_id")
    private Long driverId;

    @JsonProperty("user_allocator_id")
    private Long userAllocatorId;

    @JsonProperty("route_assignment_role")
    private RouteAssignmentRole routeAssignmentRole;
}