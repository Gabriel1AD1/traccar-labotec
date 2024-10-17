package com.labotec.traccar.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "tbl_lab_programacion", schema = "traccar_db")
public class Programacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lab_id_programacion", nullable = false)
    private Integer id;

    @Column(name = "lab_fecha_hora_salida", nullable = false)
    private Instant labFechaHoraSalida;

    @Column(name = "lab_fecha_hora_llegada", nullable = false)
    private Instant labFechaHoraLlegada;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lab_id_unidad", nullable = false)
    private Unidades labIdUnidad;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lab_id_chofer", nullable = false)
    private Chofer labIdChofer;

    @Column(name = "lab_id_local", nullable = false)
    private Integer labIdLocal;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lab_id_ruta", nullable = false)
    private Ruta labIdRuta;

    @Column(name = "lab_num_planillas", length = 50)
    private String labNumPlanillas;

    @Column(name = "lab_estado", nullable = false)
    private Byte labEstado;

    @Column(name = "lab_fecha_hora_salida_estimada", nullable = false)
    private Instant labFechaHoraSalidaEstimada;

    @Column(name = "lab_fecha_hora_llegada_estimada", nullable = false)
    private Instant labFechaHoraLlegadaEstimada;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lab_id_empresa", nullable = false)
    private Empresa labIdEmpresa;

}