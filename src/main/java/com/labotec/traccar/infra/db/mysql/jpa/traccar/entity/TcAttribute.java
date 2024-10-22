package com.labotec.traccar.infra.db.mysql.jpa.traccar.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tc_attributes", schema = "traccar")
public class TcAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 4000)
    @NotNull
    @Column(name = "description", nullable = false, length = 4000)
    private String description;

    @Size(max = 128)
    @NotNull
    @Column(name = "type", nullable = false, length = 128)
    private String type;

    @Size(max = 128)
    @NotNull
    @Column(name = "attribute", nullable = false, length = 128)
    private String attribute;

    @Size(max = 4000)
    @NotNull
    @Column(name = "expression", nullable = false, length = 4000)
    private String expression;

    @NotNull
    @Column(name = "priority", nullable = false)
    private Integer priority;

}