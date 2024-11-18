package com.labotec.traccar.infra.db.mysql.jpa.labotec.entity;

import com.labotec.traccar.domain.enums.STATE;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Data
@Builder
@Entity
@Table(
        name = "tbl_programacion",
        schema = "traccar_db",
        indexes = {
                @Index(name = "idx_id_company", columnList = "id, empresa_id"),
                @Index(name = "idx_id_user", columnList = "id, usuario_id"),
                @Index(name = "idx_company", columnList = "empresa_id"),
                @Index(name = "idx_user", columnList = "usuario_id"),
        }
)
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private UserEntity userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id")
    private CompanyEntity companyId;

    @Column(name = "fecha_hora_salida", nullable = false)
    private Instant departureTime;

    @Column(name = "fecha_hora_llegada", nullable = false)
    private Instant arrivalTime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_unidad", nullable = false)
    private VehicleEntity vehicle;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_chofer", nullable = false)
    private DriverEntity driver;

    @JoinColumn(name = "id_local", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private LocationEntity location;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_ruta", nullable = false)
    private RouteEntity route;

    @Column(name = "num_planillas", length = 50)
    private String sheetNumber;

    @Column(name = "estado", nullable = false)
    private STATE status;

    @Column(name = "porcentaje_completado")
    private Double percentageTraveled; // Inicia como 0.0 al crearse

    @Column(name = "fecha_hora_salida_estimada", nullable = false)
    private Instant estimatedDepartureTime;

    @Column(name = "fecha_hora_llegada_estimada", nullable = false)
    private Instant estimatedArrivalTime;

    @ManyToOne(fetch = FetchType.LAZY , optional = false)
    @JoinColumn(name= "id_geofence_poligonal" , nullable = false)
    private CircularGeofenceEntity geofence;

    @CreatedDate
    @Column(name = "fecha_creacion", updatable = false)
    private Instant createdDate;

    @LastModifiedDate
    @Column(name = "fecha_actualizacion")
    private Instant lastModifiedDate;

}