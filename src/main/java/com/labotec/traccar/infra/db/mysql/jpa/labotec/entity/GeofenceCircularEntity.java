package com.labotec.traccar.infra.db.mysql.jpa.labotec.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class GeofenceCircularEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    // Punto central
    private double latitude;
    private double longitude;

    // Radio de la geocerca
    private double radius;

}