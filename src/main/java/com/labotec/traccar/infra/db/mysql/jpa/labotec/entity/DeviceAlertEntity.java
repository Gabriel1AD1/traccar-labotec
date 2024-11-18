package com.labotec.traccar.infra.db.mysql.jpa.labotec.entity;

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
        name = "tc_device_alerts",
        schema = "traccar_db",
        indexes = {
                @Index(name = "idx_id", columnList = "id"),
                @Index(name = "idx_id_company", columnList = "id, empresa_id"),
                @Index(name = "idx_id_user", columnList = "id, usuario_id"),
                @Index(name = "idx_company", columnList = "empresa_id"),
                @Index(name = "idx_user", columnList = "usuario_id")
        }
)
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class DeviceAlertEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private UserEntity userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id")
    private CompanyEntity companyId;

    @Column(name = "device_id", nullable = false)
    private long deviceId;

    @Column(name = "alert_time", nullable = false)
    private Instant alertTime;

    @Column(name = "alert_type", nullable = false)
    private String alertType;

    @Column(name = "duration", nullable = true)
    private Integer duration;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "speed")
    private double speed;

    @Column(name = "course")
    private double course;

    @Column(name = "altitude")
    private double altitude;

    @Column(name = "address", length = 512)
    private String address;

    @Column(name = "accuracy")
    private double accuracy;

    @Column(name = "description", length = 1024)
    private String description;

    @CreatedDate
    @Column(name = "fecha_creacion", updatable = false)
    private Instant createdDate;

    @LastModifiedDate
    @Column(name = "fecha_actualizacion")
    private Instant lastModifiedDate;
}

