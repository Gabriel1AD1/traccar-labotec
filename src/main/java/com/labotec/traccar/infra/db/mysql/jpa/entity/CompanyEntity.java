package com.labotec.traccar.infra.db.mysql.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "tbl_lab_empresa", schema = "traccar_db")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyEntity {
    @Id
    @Column(name = "lab_id_empresa", nullable = false)
    private Integer id;

    @Column(name = "lab_nombre", length = 45)
    private String labNombre;

    @Column(name = "lab_ruc", nullable = false, length = 45)
    private String labRuc;

    @Column(name = "lab_estado")
    private Byte labEstado;

}