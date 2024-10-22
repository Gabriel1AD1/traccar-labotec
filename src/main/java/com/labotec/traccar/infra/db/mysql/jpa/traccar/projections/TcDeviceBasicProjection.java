package com.labotec.traccar.infra.db.mysql.jpa.traccar.projections;

public interface TcDeviceBasicProjection {
    Integer getId();
    String getName();
    String getUniqueid();
}