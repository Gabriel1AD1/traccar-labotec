package com.labotec.traccar.infra.db.mysql.jpa.labotec.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.Instant;

@Entity
@Table(name = "tbl_registro_paradero", indexes = {
        @Index(name = "idx_programacion_asignada", columnList = "programacion_asignada_id") // Índice sobre la columna scheduleId
})
@Data
public class StopRegisterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática del ID
    private Long id;

    @Column(name = "id_vehiculo", nullable = false) // ID del vehículo
    private Long vehicleId;

    @Column(name = "programacion_asignada_id")
    private Long scheduleId;

    @Column(name = "paradero_id", nullable = false) // Nombre o ID del paradero
    private Long busStopId;

    @Column(name = "hora_entrada") // Hora de entrada al paradero
    private Instant entryTime;

    @Column(name = "hora_salida") // Hora de salida del paradero
    private Instant exitTime;

    @Column(name = "tiempo_programado", nullable = false) // Tiempo programado en minutos
    private Integer scheduledTime;

    @Column(name = "tiempo_estimado", nullable = false) // Tiempo estimado en minutos
    private Integer estimatedTime;

    @Column(name = "exceso_tiempo", nullable = false) // Indica si el tiempo excedió lo programado
    private Boolean timeExceeded;
    @Column(name = "tiempo_minimo_cumplido")
    private Boolean isMinimumTimeMet;
    @Column(name = "alertado", nullable = false) // Indica si se generó una alerta
    private Boolean alerted;

}
