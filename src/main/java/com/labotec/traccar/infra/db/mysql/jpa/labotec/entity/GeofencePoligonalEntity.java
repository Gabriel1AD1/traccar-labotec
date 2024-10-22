package com.labotec.traccar.infra.db.mysql.jpa.labotec.entity;


import jakarta.persistence.*;

@Entity
public class GeofencePoligonalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "geofence_id")
    private List<PointEntity> points;
}