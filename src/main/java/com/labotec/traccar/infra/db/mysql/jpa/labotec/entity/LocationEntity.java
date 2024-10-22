package com.labotec.traccar.infra.db.mysql.jpa.labotec.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@Entity
@Table(name = "tbl_lab_local", schema = "traccar_db")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)

public class LocationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lab_id_local", nullable = false)
    private Integer id;

    @Column(name = "lab_nombre", nullable = false, length = 200)
    private String name;

    @Column(name = "lab_dat_latitud", length = 100)
    private String latitude;

    @Column(name = "lab_dat_longitud", length = 100)
    private String longitude;

    @Column(name = "lab_dat_radio", precision = 10, scale = 2)
    private BigDecimal radius;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lab_id_empresa", nullable = false)
    private CompanyEntity company;

    @CreatedDate
    @Column(name = "lab_fecha_creacion", updatable = false)
    private Instant createdDate;

    @LastModifiedDate
    @Column(name = "lab_fecha_actualizacion")
    private Instant lastModifiedDate;
}
