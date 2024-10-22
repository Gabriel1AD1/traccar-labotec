package com.labotec.traccar.infra.db.mysql.jpa.traccar.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "databasechangeloglock", schema = "traccar")
public class Databasechangeloglock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "LOCKED", nullable = false)
    private Boolean locked = false;

    @Column(name = "LOCKGRANTED")
    private Instant lockgranted;

    @Size(max = 255)
    @Column(name = "LOCKEDBY")
    private String lockedby;

}