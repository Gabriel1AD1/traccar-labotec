package com.labotec.traccar.infra.db.mysql.jpa.labotec.entity;

import com.labotec.traccar.domain.enums.STATE;
import com.labotec.traccar.domain.enums.TypeGeofence;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.List;

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
                @Index(name = "idx_user", columnList = "usuario_id")
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

    @Column(name = "fecha_hora_salida", nullable = true)
    private Instant departureTime;

    @Column(name = "fecha_hora_llegada", nullable = true)
    private Instant arrivalTime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_device_traccar", nullable = false)
    private VehicleEntity vehicle;

    @JoinColumn(name = "id_local", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private LocationEntity location;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_ruta", nullable = false)
    private RouteEntity route;
    @OneToMany(mappedBy = "scheduleId", cascade = CascadeType.ALL, orphanRemoval = true , fetch = FetchType.EAGER)
    private List<DriverScheduleEntity> drivers;
    @Column(name = "num_planillas", length = 50)
    private String sheetNumber;

    @Column(name = "estado", nullable = false)
    private STATE status;

    @Column(name = "porcentaje_completado")
    private Double percentageTraveled; // Inicia como 0.0 al crearse


    @Column(name = "fecha_hora_salida_estimada", nullable = false)
    private Instant estimatedDepartureTime;
    // Relación uno a muchos con conductores
    @Column(name = "programacion_completada")
    private Boolean isProgramCompleted;

    @Column(name = "fecha_hora_llegada_estimada", nullable = false)
    private Instant estimatedArrivalTime;
    @Column(name = "geofence_id", nullable = true)
    private Long geofenceId; // Almacena el ID de la geocerca

    @Column(name = "radio_validador_polilynea")
    private Long radiusValidateRoutePolyline;

    @Column(name = "validdar ruta_por_polilyne")
    private Boolean validateRouteExplicit;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_geocerca", nullable = false, length = 30)
    private TypeGeofence geofenceType; // Almacena el tipo de geocerca
    @CreatedDate
    @Column(name = "fecha_creacion", updatable = false)
    private Instant createdDate;

    @LastModifiedDate
    @Column(name = "fecha_actualizacion")
    private Instant lastModifiedDate;

}