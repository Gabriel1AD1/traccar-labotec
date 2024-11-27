package com.labotec.traccar.infra.db.mysql.jpa.labotec.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
        name = "tbl_programacion_chofer",
        schema = "traccar_db",
        indexes = {
                @Index(name = "idx_schedule_driver", columnList = "id_programacion, id_chofer")
        }
)
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class DriverScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_programacion", nullable = false)
    @JsonIgnore
    private ScheduleEntity scheduleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_chofer", nullable = false)
    private DriverEntity driverId;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol_asignado", nullable = false)
    private ROUTE_ASSIGNMENT_ROLE routeAssignmentRole;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_asignador")
    private UserEntity userAllocatorId;

    @CreatedDate
    @Column(name = "fecha_creacion", updatable = false)
    private Instant createdDate;

    @LastModifiedDate
    @Column(name = "fecha_actualizacion")
    private Instant lastModifiedDate;
}
