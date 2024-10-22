package com.labotec.traccar.infra.db.mysql.jpa.traccar.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tc_maintenances", schema = "traccar")
public class TcMaintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 4000)
    @NotNull
    @Column(name = "name", nullable = false, length = 4000)
    private String name;

    @Size(max = 128)
    @NotNull
    @Column(name = "type", nullable = false, length = 128)
    private String type;

    @NotNull
    @Column(name = "start", nullable = false)
    private Double start;

    @NotNull
    @Column(name = "period", nullable = false)
    private Double period;

    @Size(max = 4000)
    @NotNull
    @Column(name = "attributes", nullable = false, length = 4000)
    private String attributes;

}