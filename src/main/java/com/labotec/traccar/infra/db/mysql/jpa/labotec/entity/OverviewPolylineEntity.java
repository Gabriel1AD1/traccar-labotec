package com.labotec.traccar.infra.db.mysql.jpa.labotec.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tbl_lab_overview_polyline", schema = "traccar_db")
public class OverviewPolylineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tbl_route_id")
    private RouteEntity route;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tbl_route_bus_stop_id")
    private RouteBusStopEntity routeBusStop;

    @Column(name = "tbl_lab_polyline", length = 5000)
    private String polyline;

    @Column(name = "tbl_lab_is_primary")
    private Boolean isPrimary;

    @CreatedDate
    @Column(name = "lab_fecha_creacion", updatable = false)
    private Instant createdDate;

    @LastModifiedDate
    @Column(name = "lab_fecha_actualizacion")
    private Instant lastModifiedDate;

}
