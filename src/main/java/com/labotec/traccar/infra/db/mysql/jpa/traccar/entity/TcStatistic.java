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
@Table(name = "tc_statistics", schema = "traccar")
public class TcStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "capturetime", nullable = false)
    private Instant capturetime;

    @NotNull
    @Column(name = "activeusers", nullable = false)
    private Integer activeusers;

    @NotNull
    @Column(name = "activedevices", nullable = false)
    private Integer activedevices;

    @NotNull
    @Column(name = "requests", nullable = false)
    private Integer requests;

    @NotNull
    @Column(name = "messagesreceived", nullable = false)
    private Integer messagesreceived;

    @NotNull
    @Column(name = "messagesstored", nullable = false)
    private Integer messagesstored;

    @Size(max = 4096)
    @NotNull
    @Column(name = "attributes", nullable = false, length = 4096)
    private String attributes;

    @NotNull
    @Column(name = "mailsent", nullable = false)
    private Integer mailsent;

    @NotNull
    @Column(name = "smssent", nullable = false)
    private Integer smssent;

    @NotNull
    @Column(name = "geocoderrequests", nullable = false)
    private Integer geocoderrequests;

    @NotNull
    @Column(name = "geolocationrequests", nullable = false)
    private Integer geolocationrequests;

    @Size(max = 4096)
    @Column(name = "protocols", length = 4096)
    private String protocols;

}