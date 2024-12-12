package com.labotec.traccar.infra.db.mysql.jpa.labotec.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@Entity
@Table(
        name = "tbl_ruta_paradero",
        schema = "traccar_db",
        indexes = {
                @Index(name = "idx_id_ruta", columnList = "id_ruta"),
        }
)
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class RouteBusStopEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_ruta", nullable = false)
    @JsonIgnore
    @JsonBackReference
    private RouteEntity route;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_primer_paradero", nullable = false)
    private BusStopEntity firstBusStop;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_segundo_paradero", nullable = false)
    private BusStopEntity secondBusStop;
    @OneToMany(mappedBy = "routeBusStop", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonManagedReference
    private List<OverviewPolylineEntity> overviewPolylines;
    @Column(name = "orden", nullable = false)
    private Long order;

    @CreatedDate
    @Column(name = "fecha_creacion", updatable = false)
    private Instant createdDate;
    @LastModifiedDate
    @Column(name = "fecha_actualizacion")
    private Instant lastModifiedDate;
}
