package com.labotec.traccar.domain.web.dto.labotec.response;

import com.labotec.traccar.domain.database.models.Driver;
import com.labotec.traccar.domain.database.models.User;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.ROUTE_ASSIGNMENT_ROLE;
import lombok.Data;

import java.time.Instant;

@Data
public class DriverScheduleResponse {
    private Long id;
    private Long scheduleId;
    private Long driverId;
    private Long userAllocatorId;
    private ROUTE_ASSIGNMENT_ROLE routeAssignmentRole;
}
