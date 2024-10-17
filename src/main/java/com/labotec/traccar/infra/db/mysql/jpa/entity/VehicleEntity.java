package com.labotec.traccar.infra.db.mysql.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "tbl_lab_unidades", schema = "traccar_db")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lab_id_unidad", nullable = false)
    private Integer id;

    @Column(name = "lab_num_placa", nullable = false, length = 50)
    private String labNumPlaca;

    @Column(name = "lab_estado", nullable = false)
    private Byte labEstado;

    @Column(name = "lab_padron", nullable = false, length = 50)
    private String labPadron;

    @Column(name = "lab_marca", nullable = false, length = 50)
    private String labMarca;

    @Column(name = "lab_modelo", nullable = false, length = 50)
    private String labModelo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lab_id_empresa", nullable = false)
    private CompanyEntity labIdCompanyEntity;

}