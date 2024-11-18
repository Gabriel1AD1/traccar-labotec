package com.labotec.traccar.infra.db.mysql.jpa.labotec.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class ProgressRouteE {
    @Column(name = "route_bus_stop_id")
    private Long routeBusStopId;

    @Column(name = "percentage_completed")
    private Double percentage;
}