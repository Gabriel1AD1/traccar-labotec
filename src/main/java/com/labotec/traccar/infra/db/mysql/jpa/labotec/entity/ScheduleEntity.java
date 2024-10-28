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
@Table(name = "tbl_lab_programacion", schema = "traccar_db")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)

public class ScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lab_id_programacion", nullable = false)
    private Integer id;

    @Column(name = "lab_fecha_hora_salida", nullable = false)
    private Instant departureTime;

    @Column(name = "lab_fecha_hora_llegada", nullable = false)
    private Instant arrivalTime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lab_id_unidad", nullable = false)
    private VehicleEntity vehicle;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lab_id_chofer", nullable = false)
    private DriverEntity driver;

    @JoinColumn(name = "lab_id_local", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private LocationEntity location;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lab_id_ruta", nullable = false)
    private RouteEntity route;

    @Column(name = "lab_num_planillas", length = 50)
    private String sheetNumber;

    @Column(name = "lab_estado", nullable = false)
    private Byte status;

    @Column(name = "lab_fecha_hora_salida_estimada", nullable = false)
    private Instant estimatedDepartureTime;

    @Column(name = "lab_fecha_hora_llegada_estimada", nullable = false)
    private Instant estimatedArrivalTime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lab_id_empresa", nullable = false)
    private CompanyEntity company;

    @ManyToOne(fetch = FetchType.LAZY , optional = false)
    @JoinColumn(name= "lab_id_geofence_poligonal" , nullable = false)
    private CircularGeofenceEntity geofence;

    @CreatedDate
    @Column(name = "lab_fecha_creacion", updatable = false)
    private Instant createdDate;

    @LastModifiedDate
    @Column(name = "lab_fecha_actualizacion")
    private Instant lastModifiedDate;

}