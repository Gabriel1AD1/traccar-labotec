package com.labotec.traccar.domain.database.models;


import lombok.Data;

import java.util.List;

@Data
public class GeofencePoligonal {

    private Long id;
    private String name;
    private String description;
    private List<Point> points;

}