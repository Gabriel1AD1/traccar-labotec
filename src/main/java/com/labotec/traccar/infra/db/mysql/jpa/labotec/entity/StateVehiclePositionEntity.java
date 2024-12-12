package com.labotec.traccar.infra.db.mysql.jpa.labotec.entity;

import com.labotec.traccar.domain.enums.STATE_VEHICLE;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(
        name = "tbl_estado_vehiculo_posicion",
        indexes = {
                @Index(name = "idx_vehicle_associate", columnList = "vehicle_id")
        }
)
public class StateVehiclePositionEntity {

    @Id
    @Column(name = "vehicle_id")
    private Long vehicleAssociate;  // Relación uno a uno con el vehículo
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longitude")
    private Double longitude;
    @Column(name = "ultimo_tiempo_detenido")
    private Instant timeInstantStopped;
    @Column(name = "minutos_detenido")
    private Integer timeStoppedInMinutes;
    @ManyToOne
    @JoinColumn(name = "ultimo_paradero_registrado")
    private BusStopEntity lastUpdateBusStop;
    @Enumerated(EnumType.STRING)
    @Column(name = "state_vehicle")
    private STATE_VEHICLE stateVehicle;
}
