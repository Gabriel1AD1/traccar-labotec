package com.labotec.traccar.infra.db.mysql.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "tbl_lab_ruta", schema = "traccar_db")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lab_id_ruta", nullable = false)
    private Integer id;

    @Column(name = "lab_nombre", nullable = false, length = 250)
    private String name;

    @Column(name = "lab_estado", nullable = false)
    private Byte status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lab_id_paradero_origen")
    private BusStopEntity originBusStop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lab_id_paradero_destino")
    private BusStopEntity destinationBusStop;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lab_id_empresa", nullable = false)
    private CompanyEntity company;
}
