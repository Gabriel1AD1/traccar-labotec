package com.labotec.traccar.infra.db.mysql.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "tbl_lab_paradero", schema = "traccar_db")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusStopEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lab_id_paradero", nullable = false)
    private Integer id;

    @Column(name = "lab_nombre", length = 200)
    private String name;

    @Column(name = "lab_dat_latitud", length = 100)
    private String latitude;

    @Column(name = "lab_dat_longitud", length = 100)
    private String longitude;

    @Column(name = "lab_estado", nullable = false)
    private Byte status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lab_id_empresa", nullable = false)
    private CompanyEntity company;
}
