package com.labotec.traccar.domain.database.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
@Builder
public class StopRegister {
    private Long id;                  // ID del registro
    private Long vehicleId;         // ID del vehículo
    private Long scheduleId;          // ID de la programación asignada
    private Long busStopId;           // ID del paradero
    private Instant entryTime;        // Hora de entrada al paradero
    private Instant exitTime;         // Hora de salida del paradero
    private Integer scheduledTime;    // Tiempo programado en minutos
    private Integer estimatedTime;
    private Boolean isMinimumTimeMet;
    private Boolean timeExceeded;     // Indica si el tiempo excedió lo programado
    private Boolean alerted;          // Indica si se generó una alerta
    private Integer maxTimeExcess; // Minutos que se pasó del tiempo máximo

    private Integer minTimeShortfall; // Minutos que se fue del tiempo mínimo
    public StopRegister() {

    }
}
