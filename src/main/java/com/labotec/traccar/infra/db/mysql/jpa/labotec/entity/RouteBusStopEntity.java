package com.labotec.traccar.infra.db.mysql.jpa.labotec.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.labotec.traccar.domain.database.models.BusStop;
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
@Table(name = "tbl_lab_ruta_paradero", schema = "traccar_db")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class RouteBusStopEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lab_id_ruta_paradero", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lab_id_ruta", nullable = false)
    @JsonIgnore
    private RouteEntity route;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lab_id_primer_paradero", nullable = false)
    private BusStopEntity firstBusStop;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lab_id_segundo_paradero", nullable = false)
    private BusStopEntity secondBusStop;

    @Column(name = "lab_orden", nullable = false)
    private Integer order;
    @Column(name = "lab_completed")
    private Boolean completed;

    @CreatedDate
    @Column(name = "lab_fecha_creacion", updatable = false)
    private Instant createdDate;

    @LastModifiedDate
    @Column(name = "lab_fecha_actualizacion")
    private Instant lastModifiedDate;
}
