package com.labotec.traccar.infra.db.mysql.jpa.traccar.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tc_drivers", schema = "traccar", indexes = {
        @Index(name = "uniqueid", columnList = "uniqueid", unique = true),
        @Index(name = "idx_drivers_uniqueid", columnList = "uniqueid")
})
public class TcDriver {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 128)
    @NotNull
    @Column(name = "name", nullable = false, length = 128)
    private String name;

    @Size(max = 128)
    @NotNull
    @Column(name = "uniqueid", nullable = false, length = 128)
    private String uniqueid;

    @Size(max = 4000)
    @NotNull
    @Column(name = "attributes", nullable = false, length = 4000)
    private String attributes;

}