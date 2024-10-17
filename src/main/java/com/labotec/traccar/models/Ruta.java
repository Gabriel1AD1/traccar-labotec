package com.labotec.traccar.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_lab_ruta", schema = "traccar_db")
public class Ruta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lab_id_ruta", nullable = false)
    private Integer id;

    @Column(name = "lab_nombre", nullable = false, length = 250)
    private String labNombre;

    @Column(name = "lab_estado", nullable = false)
    private Byte labEstado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lab_id_paradero_origen")
    private Paradero labIdParaderoOrigen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lab_id_paradero_destino")
    private Paradero labIdParaderoDestino;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lab_id_empresa", nullable = false)
    private Empresa labIdEmpresa;

}