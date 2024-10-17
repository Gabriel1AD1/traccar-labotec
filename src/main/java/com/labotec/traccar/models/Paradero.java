package com.labotec.traccar.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_lab_paradero", schema = "traccar_db")
public class Paradero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lab_id_paradero", nullable = false)
    private Integer id;

    @Column(name = "lab_nombre", length = 200)
    private String labNombre;

    @Column(name = "lab_dat_latitud", length = 100)
    private String labDatLatitud;

    @Column(name = "lab_dat_longitud", length = 100)
    private String labDatLongitud;

    @Column(name = "lab_estado", nullable = false)
    private Byte labEstado;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lab_id_empresa", nullable = false)
    private Empresa labIdEmpresa;

}