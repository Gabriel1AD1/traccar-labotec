package com.labotec.traccar.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_lab_empresa", schema = "traccar_db")
public class Empresa {
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