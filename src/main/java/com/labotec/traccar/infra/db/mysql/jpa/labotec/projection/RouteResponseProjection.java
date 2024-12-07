package com.labotec.traccar.infra.db.mysql.jpa.labotec.projection;

import com.labotec.traccar.app.enums.RouteType;

public interface RouteResponseProjection {
    Long getId();

    String getName();

    Long getDistanceMaxInKM();

    Long getDistanceMinInKM();

    RouteType getRouteType();
}
