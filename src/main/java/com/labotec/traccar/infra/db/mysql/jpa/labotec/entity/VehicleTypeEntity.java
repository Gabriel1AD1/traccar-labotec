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
@Table(name = "tbl_lab_tipo_vehiculo", schema = "traccar_db")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public class VehicleTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lab_id_tipo_vehiculo", nullable = false)
    private Integer id;

    @Column(name = "lab_nombre_tipo", nullable = false, length = 50)
    private String name;

    @CreatedDate
    @Column(name = "lab_fecha_creacion", updatable = false)
    private Instant createdDate;

    @LastModifiedDate
    @Column(name = "lab_fecha_actualizacion")
    private Instant lastModifiedDate;

    public VehicleTypeEntity(String name) {
        this.name = name;
    }
}
