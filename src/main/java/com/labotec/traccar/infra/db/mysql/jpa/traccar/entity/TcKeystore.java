package com.labotec.traccar.infra.db.mysql.jpa.traccar.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tc_keystore", schema = "traccar")
public class TcKeystore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "publickey", nullable = false)
    private byte[] publickey;

    @NotNull
    @Column(name = "privatekey", nullable = false)
    private byte[] privatekey;

}