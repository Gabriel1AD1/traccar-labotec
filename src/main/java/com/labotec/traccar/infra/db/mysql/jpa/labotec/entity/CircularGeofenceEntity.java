package com.labotec.traccar.infra.db.mysql.jpa.labotec.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Data
@Builder
@Entity
@Table(name = "tbl_geo_cerca_circular", schema = "traccar_db")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CircularGeofenceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lab_id_geofence", nullable = false)
    private Long id;
    @Column(name = "lab_name", nullable = false, length = 100)
    private String name;
    @Column(name = "lab_description", length = 255)
    private String description;
    @Column(name = "lab_latitude")
    private Double latitude;
    @Column(name = "lab_longitude")
    private Double longitude;
    @Column(name = "lab_radius")
    private Double radius;
    @CreatedDate
    @Column(name = "lab_fecha_creacion", updatable = false, nullable = false)
    private Instant createdDate;
    @LastModifiedDate
    @Column(name = "lab_fecha_actualizacion", nullable = false)
    private Instant lastModifiedDate;
}
