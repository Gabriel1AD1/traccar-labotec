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

import static com.labotec.traccar.infra.db.mysql.jpa.labotec.constant.CONFIG_SCHEMA.SCHEMA;

@Data
@Builder
@Entity
@Table(     name = "tbl_notificaciones",
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
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name = "titulo")
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private UserEntity userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id")
    private CompanyEntity companyId;
    @Column(nullable = false, name = "contenido")
    private String content;

    @CreatedDate
    @Column(name = "lab_fecha_creacion", updatable = false)
    private Instant createdDate;

    @LastModifiedDate
    @Column(name = "lab_fecha_actualizacion")
    private Instant lastModifiedDate;

    public NotificationEntity(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
