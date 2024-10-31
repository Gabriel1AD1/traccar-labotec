package com.labotec.traccar.infra.db.mysql.jpa.traccar.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "tc_devices", schema = "traccar")
public class TcDevice {
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
    @Column(name = "uniqueid", nullable = false, length = 128)
    private String uniqueid;

    @Column(name = "lastupdate")
    private Instant lastupdate;

    @Column(name = "positionid")
    private Integer positionid;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "groupid")
    private TcGroup groupid;

    @Size(max = 4000)
    @Column(name = "attributes", length = 4000)
    private String attributes;

    @Size(max = 128)
    @Column(name = "phone", length = 128)
    private String phone;

    @Size(max = 128)
    @Column(name = "model", length = 128)
    private String model;

    @Size(max = 512)
    @Column(name = "contact", length = 512)
    private String contact;

    @Size(max = 128)
    @Column(name = "category", length = 128)
    private String category;

    @ColumnDefault("b'0'")
    @Column(name = "disabled")
    private Boolean disabled;

    @Size(max = 8)
    @Column(name = "status", length = 8)
    private String status;

    @Column(name = "expirationtime")
    private Instant expirationtime;

    @ColumnDefault("b'0'")
    @Column(name = "motionstate")
    private Boolean motionstate;

    @Column(name = "motiontime")
    private Instant motiontime;

    @ColumnDefault("0")
    @Column(name = "motiondistance")
    private Double motiondistance;

    @ColumnDefault("b'0'")
    @Column(name = "overspeedstate")
    private Boolean overspeedstate;

    @Column(name = "overspeedtime")
    private Instant overspeedtime;

    @ColumnDefault("0")
    @Column(name = "overspeedgeofenceid")
    private Integer overspeedgeofenceid;

    @ColumnDefault("b'0'")
    @Column(name = "motionstreak")
    private Boolean motionstreak;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "calendarid")
    private TcCalendar calendarid;

    @NotNull
    @ColumnDefault("b'0'")
    @Column(name = "isStopped", nullable = false)
    private Boolean isStopped = false;

    @NotNull
    @ColumnDefault("b'0'")
    @Column(name = "alertSent", nullable = false)
    private Boolean alertSent = false;

    @Column(name = "lastStoppedTime")
    private Instant lastStoppedTime;

}