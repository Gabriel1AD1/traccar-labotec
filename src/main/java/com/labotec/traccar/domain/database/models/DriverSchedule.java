package com.labotec.traccar.domain.database.models;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.ROUTE_ASSIGNMENT_ROLE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverSchedule {
    private Long id;
    private Schedule scheduleId;
    private Driver driverId;
    private User userAllocatorId;
    private ROUTE_ASSIGNMENT_ROLE routeAssignmentRole;
    private Instant createdDate;
    private Instant lastModifiedDate;

}
