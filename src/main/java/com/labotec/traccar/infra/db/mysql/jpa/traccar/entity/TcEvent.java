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
@Table(name = "tc_events", schema = "traccar", indexes = {
        @Index(name = "event_deviceid_servertime", columnList = "eventtime, deviceid")
})
public class TcEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 128)
    @NotNull
    @Column(name = "type", nullable = false, length = 128)
    private String type;

    @NotNull
    @Column(name = "eventtime", nullable = false)
    private Instant eventtime;

    @Column(name = "deviceid")
    private Integer deviceid;

    @Column(name = "positionid")
    private Integer positionid;

    @Column(name = "geofenceid")
    private Integer geofenceid;

    @Size(max = 4000)
    @Column(name = "attributes", length = 4000)
    private String attributes;

    @Column(name = "maintenanceid")
    private Integer maintenanceid;

}