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
@Table(name = "tbl_lab_unidades", schema = "traccar_db")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)

public class VehicleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lab_id_unidad", nullable = false)
    private Integer id;

    @Column(name = "lab_id_device_traccar", nullable = false, unique = true)
    private Integer traccarDeviceId;

    @Column(name = "lab_num_placa", nullable = false, length = 50)
    private String licensePlate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lab_id_tipo_vehiculo", nullable = false)
    private VehicleTypeEntity typeVehicle;

    @Column(name = "lab_estado", nullable = false)
    private Byte status;

    @Column(name = "lab_padron", nullable = false, length = 50)
    private String registerNumber;

    @Column(name = "lab_marca", nullable = false, length = 50)
    private String brand;

    @Column(name = "lab_modelo", nullable = false, length = 50)
    private String model;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lab_id_empresa", nullable = false)
    private CompanyEntity company;

    @CreatedDate
    @Column(name = "lab_fecha_creacion", updatable = false)
    private Instant createdDate;

    @LastModifiedDate
    @Column(name = "lab_fecha_actualizacion")
    private Instant lastModifiedDate;


}