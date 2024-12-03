package com.labotec.traccar.infra.db.mysql.jpa.labotec.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
@Table(name = "tbl_vehicle_position") // Especifica el nombre de la tabla en la base de datos, si es necesario.
public class VehiclePositionEntity {

    @Id
    @Column(name = "device_id") // El nombre de la columna en la base de datos
    private Long deviceId;

    @Column(name = "programacion_id",unique = true)
    private Long scheduleId;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;
    @Column(name = "current_time_stopped_for_bus_stop")
    private Instant currentTimeStoppedForBusStop;

    @Column(name = "current_time_stopped")
    private Instant currentTimeStopped;
    @Column(name = "minimo_tiempo_por_paradero")
    private Integer minWaitTimeForBusStop;
    @Column(name = "maximo_tiempo_por_paradero")
    private Integer maxWaitTimeForBusStop;
    @Column(name = "current_segment")
    private Long currentSegment;
    @Column(name = "current_bus_stop")
    private Long currentBusStop;
    @Column(name = "se_encuentra_en_paradero")
    private boolean whereaboutsStatus;

    @Column(name = "next_bus_stop_id")
    private Long nexBusStopId;
    @Column(name = "ruta_reiniciada")
    private boolean resetRoute;

    @Column(name = "min_next_bus_stop_time")
    private Instant nextMinBusStopTimeBusStop;
    @Column(name = "max_next_bus_stop_time")
    private Instant nextMaxBusStopTimeBusStop;

}
