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
@Table(name = "tc_users", schema = "traccar", indexes = {
        @Index(name = "email", columnList = "email", unique = true),
        @Index(name = "idx_users_email", columnList = "email"),
        @Index(name = "idx_users_login", columnList = "login")
})
public class TcUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 128)
    @NotNull
    @Column(name = "name", nullable = false, length = 128)
    private String name;

    @Size(max = 128)
    @NotNull
    @Column(name = "email", nullable = false, length = 128)
    private String email;

    @Size(max = 128)
    @Column(name = "hashedpassword", length = 128)
    private String hashedpassword;

    @Size(max = 128)
    @Column(name = "salt", length = 128)
    private String salt;

    @NotNull
    @Column(name = "readonly", nullable = false)
    private Boolean readonly = false;

    @Column(name = "administrator")
    private Boolean administrator;

    @Size(max = 128)
    @Column(name = "map", length = 128)
    private String map;

    @NotNull
    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @NotNull
    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @NotNull
    @Column(name = "zoom", nullable = false)
    private Integer zoom;

    @Size(max = 4000)
    @Column(name = "attributes", length = 4000)
    private String attributes;

    @Size(max = 128)
    @Column(name = "coordinateformat", length = 128)
    private String coordinateformat;

    @Column(name = "disabled")
    private Boolean disabled;

    @Column(name = "expirationtime")
    private Instant expirationtime;

    @Column(name = "devicelimit")
    private Integer devicelimit;

    @Column(name = "userlimit")
    private Integer userlimit;

    @Column(name = "devicereadonly")
    private Boolean devicereadonly;

    @Size(max = 128)
    @Column(name = "phone", length = 128)
    private String phone;

    @Column(name = "limitcommands")
    private Boolean limitcommands;

    @Size(max = 128)
    @Column(name = "login", length = 128)
    private String login;

    @Size(max = 512)
    @Column(name = "poilayer", length = 512)
    private String poilayer;

    @Column(name = "disablereports")
    private Boolean disablereports;

    @Column(name = "fixedemail")
    private Boolean fixedemail;

    @Size(max = 64)
    @Column(name = "totpkey", length = 64)
    private String totpkey;

    @Column(name = "temporary")
    private Boolean temporary;

}