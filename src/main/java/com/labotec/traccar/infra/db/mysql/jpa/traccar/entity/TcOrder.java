package com.labotec.traccar.infra.db.mysql.jpa.traccar.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tc_orders", schema = "traccar")
public class TcOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 128)
    @NotNull
    @Column(name = "uniqueid", nullable = false, length = 128)
    private String uniqueid;

    @Size(max = 512)
    @Column(name = "description", length = 512)
    private String description;

    @Size(max = 512)
    @Column(name = "fromaddress", length = 512)
    private String fromaddress;

    @Size(max = 512)
    @Column(name = "toaddress", length = 512)
    private String toaddress;

    @Size(max = 4000)
    @NotNull
    @Column(name = "attributes", nullable = false, length = 4000)
    private String attributes;

}