package com.labotec.traccar.infra.db.mysql.jpa.labotec.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.labotec.traccar.domain.database.models.BusStop;
import com.labotec.traccar.domain.database.models.OverviewPolyline;
import com.labotec.traccar.domain.database.models.Route;
import com.labotec.traccar.domain.enums.TYPE_BUS_STOP;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Data
@Builder
@Entity
@Table(
        name = "tbl_ruta_paradero_segmento",
        schema = "traccar_db",
        indexes = {
                @Index(name = "idx_id_ruta", columnList = "id_ruta"),
        }
)
@NoArgsConstructor
@AllArgsConstructor
public class RouteBusStopSegmentEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_ruta", nullable = false)
    @JsonIgnore
    @JsonBackReference
    private RouteEntity route;
    @JoinColumn(name = "id_paradero", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private BusStopEntity busStop;
    @Column(name = "estimate_travel_time_to_minutes")
    private Integer estimatedTravelTime; // En minutos
    @Column(name = "max_wait_time")
    private Integer maxWaitTime; // En minutos
    @Column(name = "min_wait_time")
    private Integer minWaitTime; // En minutos
    @Column(name = "orden")
    private Long order;
    @Enumerated(EnumType.STRING)
    @Column(name = "type_bus_stop")
    private TYPE_BUS_STOP typeBusStop;

}