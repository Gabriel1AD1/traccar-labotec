package com.labotec.traccar.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "tbl_lab_local", schema = "traccar_db")
public class Local {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lab_id_local", nullable = false)
    private Integer id;

    @Column(name = "lab_nombre", nullable = false, length = 200)
    private String labNombre;

    @Column(name = "lab_dat_latitud", length = 100)
    private String labDatLatitud;

    @Column(name = "lab_dat_longitud", length = 100)
    private String labDatLongitud;

    @Column(name = "lab_dat_radio", precision = 10, scale = 2)
    private BigDecimal labDatRadio;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lab_id_empresa", nullable = false)
    private Empresa labIdEmpresa;

}