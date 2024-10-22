package com.labotec.traccar.domain.database.models;

import lombok.Data;

@Data
public class GeofenceCircular {
    private Long id;
    private String name;
    private String description;
    private double latitude;
    private double longitude;
    private double radius;
}
