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

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@Entity
@Table(
        name = "tbl_local",
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
public class LocationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private UserEntity userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id")
    private CompanyEntity companyId;

    @Column(name = "nombre", nullable = false, length = 200)
    private String name;
    @Column(name = "descripcion", nullable = false, length = 200)
    private String description;
    @Column(name = "estado")
    @Enumerated(EnumType.STRING)
    private STATE state;

    @Column(name = "latitud", length = 100)
    private String latitude;

    @Column(name = "longitud", length = 100)
    private String longitude;

    @Column(name = "radio", precision = 10, scale = 2)
    private BigDecimal radius;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paradero_id")
    private BusStopEntity busStopAssociate;

    @CreatedDate
    @Column(name = "fecha_creacion", updatable = false)
    private Instant createdDate;

    @LastModifiedDate
    @Column(name = "actualizacion")
    private Instant lastModifiedDate;
}
