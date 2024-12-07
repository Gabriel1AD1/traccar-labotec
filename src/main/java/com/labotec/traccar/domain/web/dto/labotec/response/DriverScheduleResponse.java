package com.labotec.traccar.domain.web.dto.labotec.response;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.RouteAssignmentRole;
import lombok.Data;

@Data
public class DriverScheduleResponse {
    private Long id;
    private Long scheduleId;
    private Long driverId;
    private Long userAllocatorId;
    private RouteAssignmentRole routeAssignmentRole;
}
