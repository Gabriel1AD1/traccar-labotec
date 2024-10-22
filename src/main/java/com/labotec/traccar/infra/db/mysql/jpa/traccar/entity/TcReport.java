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
@Table(name = "tc_reports", schema = "traccar")
public class TcReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 32)
    @NotNull
    @Column(name = "type", nullable = false, length = 32)
    private String type;

    @Size(max = 128)
    @NotNull
    @Column(name = "description", nullable = false, length = 128)
    private String description;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "calendarid", nullable = false)
    private TcCalendar calendarid;

    @Size(max = 4000)
    @NotNull
    @Column(name = "attributes", nullable = false, length = 4000)
    private String attributes;

}