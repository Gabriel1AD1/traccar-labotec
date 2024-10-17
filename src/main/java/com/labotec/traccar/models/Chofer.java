package com.labotec.traccar.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "tbl_lab_chofer", schema = "traccar_db")
public class Chofer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lab_id_chofer", nullable = false)
    private Integer id;

    @Column(name = "lab_nombres", length = 250)
    private String labNombres;

    @Column(name = "lab_doc_identidad")
    private Integer labDocIdentidad;

    @Column(name = "lab_num_identidad", nullable = false, length = 50)
    private String labNumIdentidad;

    @Column(name = "lab_estado", nullable = false)
    private Byte labEstado;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "lab_id_empresa", nullable = false)
    private Empresa labIdEmpresa;

}