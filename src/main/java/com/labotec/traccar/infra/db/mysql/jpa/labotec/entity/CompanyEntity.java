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
@Table(name = "tbl_lab_empresa", schema = "traccar_db")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lab_id_empresa", nullable = false)
    private Integer id;

    @Column(name = "lab_nombre", length = 45)
    private String name;

    @Column(name = "lab_ruc", nullable = false, length = 45)
    private String ruc;

    @Column(name = "lab_estado")
    private Byte status;

    @CreatedDate
    @Column(name = "lab_fecha_creacion", updatable = false)
    private Instant createdDate;

    @LastModifiedDate
    @Column(name = "lab_fecha_actualizacion")
    private Instant lastModifiedDate;

    public CompanyEntity(String name, String ruc, Byte status) {
        this.name = name;
        this.ruc = ruc;
        this.status = status;
    }
}