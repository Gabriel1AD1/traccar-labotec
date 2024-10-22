package com.labotec.traccar.infra.db.mysql.jpa.traccar.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tc_servers", schema = "traccar")
public class TcServer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "registration", nullable = false)
    private Boolean registration = false;

    @NotNull
    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @NotNull
    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @NotNull
    @Column(name = "zoom", nullable = false)
    private Integer zoom;

    @Size(max = 128)
    @Column(name = "map", length = 128)
    private String map;

    @Size(max = 128)
    @Column(name = "bingkey", length = 128)
    private String bingkey;

    @Size(max = 512)
    @Column(name = "mapurl", length = 512)
    private String mapurl;

    @NotNull
    @Column(name = "readonly", nullable = false)
    private Boolean readonly = false;

    @Size(max = 4000)
    @Column(name = "attributes", length = 4000)
    private String attributes;

    @NotNull
    @Column(name = "forcesettings", nullable = false)
    private Boolean forcesettings = false;

    @Size(max = 128)
    @Column(name = "coordinateformat", length = 128)
    private String coordinateformat;

    @Column(name = "devicereadonly")
    private Boolean devicereadonly;

    @Column(name = "limitcommands")
    private Boolean limitcommands;

    @Size(max = 512)
    @Column(name = "poilayer", length = 512)
    private String poilayer;

    @Size(max = 4000)
    @Column(name = "announcement", length = 4000)
    private String announcement;

    @Column(name = "disablereports")
    private Boolean disablereports;

    @Size(max = 512)
    @Column(name = "overlayurl", length = 512)
    private String overlayurl;

    @Column(name = "fixedemail")
    private Boolean fixedemail;

}