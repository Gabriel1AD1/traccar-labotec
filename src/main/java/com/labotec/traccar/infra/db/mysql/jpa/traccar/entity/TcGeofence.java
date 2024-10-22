package com.labotec.traccar.infra.db.mysql.jpa.traccar.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "tc_geofences", schema = "traccar")
public class TcGeofence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 128)
    @NotNull
    @Column(name = "name", nullable = false, length = 128)
    private String name;

    @Size(max = 128)
    @Column(name = "description", length = 128)
    private String description;

    @Size(max = 4096)
    @NotNull
    @Column(name = "area", nullable = false, length = 4096)
    private String area;

    @Size(max = 4000)
    @Column(name = "attributes", length = 4000)
    private String attributes;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "calendarid")
    private TcCalendar calendarid;

}