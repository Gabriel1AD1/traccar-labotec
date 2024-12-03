package com.labotec.traccar.infra.db.mysql.jpa.labotec.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.labotec.traccar.app.enums.RouteType;
import com.labotec.traccar.domain.enums.STATE;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@Entity
@Table(
        name = "tbl_ruta",
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

public class RouteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private UserEntity userId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id")
    private CompanyEntity companyId;
    @Column(name = "distancia_maxima_ruta_en_km")
    private Long distanceMaxInKM;
    @Column(name = "distancia_minima_ruta_en_km")
    private Long distanceMinInKM;
    @Column(name = "nombre", nullable = false, length = 250)
    private String name;
    @OneToMany(mappedBy = "route", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonManagedReference
    private List<RouteBusStopEntity> busStopsList;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "tipo_ruta" ,nullable = false , length = 30)
    private RouteType routeType;

    @CreatedDate
    @Column(name = "fecha_creacion", updatable = false)
    private Instant createdDate;

    @LastModifiedDate
    @Column(name = "fecha_actualizacion")
    private Instant lastModifiedDate;
}
