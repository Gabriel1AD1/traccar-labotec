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
@Table(name = "tc_notifications", schema = "traccar")
public class TcNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 128)
    @NotNull
    @Column(name = "type", nullable = false, length = 128)
    private String type;

    @Size(max = 4000)
    @Column(name = "attributes", length = 4000)
    private String attributes;

    @NotNull
    @Column(name = "always", nullable = false)
    private Boolean always = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "calendarid")
    private TcCalendar calendarid;

    @Size(max = 128)
    @Column(name = "notificators", length = 128)
    private String notificators;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "commandid")
    private TcCommand commandid;

    @Size(max = 4000)
    @Column(name = "description", length = 4000)
    private String description;

}