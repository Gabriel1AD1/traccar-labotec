package com.labotec.traccar.domain.database.models;

import lombok.Data;

@Data
public class Point {

    private double latitude;
    private double longitude;
    private int pointOrder;

}