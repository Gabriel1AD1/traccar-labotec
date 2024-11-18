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
@Table(
        name = "tbl_empresa",
        schema = "traccar_db",
        indexes = {
                @Index(name = "idx_id", columnList = "empresa_id"),
        }
)
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
public class CompanyEntity {
    @Id
    @Column(name = "empresa_id")
    private Long companyId;

    @CreatedDate
    @Column(name = "fecha_creacion", updatable = false)
    private Instant createdDate;

    @LastModifiedDate
    @Column(name = "fecha_actualizacion")
    private Instant lastModifiedDate;
}
