package com.labotec.traccar.infra.db.mysql.jpa.labotec.entity;

import com.labotec.traccar.domain.enums.STATE;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Data
@Builder
@Entity
@Table(
        name = "tbl_unidades",
        schema = "traccar_db",
        indexes = {
                @Index(name = "idx_device_company", columnList = "id_device_traccar, empresa_id"),
                @Index(name = "idx_device_user", columnList = "id_device_traccar, usuario_id")
        }

)
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)

public class VehicleEntity {
    @Id
    @Column(name = "id_device_traccar", nullable = false, unique = true)
    private Long deviceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private UserEntity userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id")
    private CompanyEntity companyId;


    @Column(name = "num_placa", nullable = false, length = 50)
    private String licensePlate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_tipo_vehiculo", nullable = false)
    private VehicleTypeEntity typeVehicle;

    @Column(name = "estado", nullable = false)
    private STATE status;

    @Column(name = "padron", nullable = false, length = 50)
    private String registerNumber;

    @Column(name = "marca", nullable = false, length = 50)
    private String brand;

    @Column(name = "modelo", nullable = false, length = 50)
    private String model;

    @CreatedDate
    @Column(name = "fecha_creacion", updatable = false)
    private Instant createdDate;

    @LastModifiedDate
    @Column(name = "fecha_actualizacion")
    private Instant lastModifiedDate;


}