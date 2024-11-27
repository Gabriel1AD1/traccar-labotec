package com.labotec.traccar.infra.db.mysql.jpa.labotec.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
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
@Table(
        name = "tbl_overview_polyline",
        schema = "traccar_db",
        indexes = {
                @Index(name = "idx_route_bus_stop", columnList = "route_bus_stop_id"),
        }


)
public class OverviewPolylineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_bus_stop_id")
    @JsonIgnore
    @JsonBackReference
    private RouteBusStopEntity routeBusStop;
    @Column(name = "polyline", length = 5000)
    private String polyline;
    @Column(name = "is_primary")
    private Boolean isPrimary;
    @CreatedDate
    @Column(name = "lab_fecha_creacion", updatable = false)
    private Instant createdDate;
    @LastModifiedDate
    @Column(name = "lab_fecha_actualizacion")
    private Instant lastModifiedDate;

}
