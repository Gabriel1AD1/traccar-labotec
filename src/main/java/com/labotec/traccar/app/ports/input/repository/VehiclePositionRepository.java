package com.labotec.traccar.app.ports.input.repository;

import com.labotec.traccar.domain.database.models.VehiclePosition;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

public interface VehiclePositionRepository {

    VehiclePosition findById(@NotNull long id);
    VehiclePosition save(@NotNull VehiclePosition vehiclePosition);
    VehiclePosition findByScheduleId(@NotNull Long scheduleId);
    /**
     * Actualiza el tiempo que el vehículo ha estado detenido en un paradero específico basado en su ubicación y la ruta asignada.
     *
     * <p>Este funcion verifica si las coordenadas actuales del vehículo (latitud y longitud) han cambiado
     * respecto a las almacenadas. Si no han cambiado, el tiempo detenido no se actualiza.
     * De lo contrario, actualiza el campo correspondiente en la base de datos.</p>
     *
     * @param scheduleId    El ID de la programación o ruta del vehículo. Este parámetro es obligatorio y
     *                      se utiliza para identificar el registro en la base de datos.
     * @param latitude      La latitud actual del vehículo. Este valor se compara con el valor registrado en
     *                      la base de datos para determinar si la ubicación ha cambiado.
     * @param longitude     La longitud actual del vehículo. Es opcional, pero si se proporciona, se compara
     *                      con el valor registrado en la base de datos.
     * @param timeNowForStop La marca de tiempo actual (`Instant`) que se utilizará para actualizar el tiempo
     *                       detenido si la ubicación ha cambiado.
     *
     * <p>Nota: Este funcion debe ser utilizado en un contexto transaccional y está diseñado para ser llamado
     * desde el servicio correspondiente.</p>
     */
    void updateTimeStopByRouteId(@NotNull Long scheduleId,
                                 @NotNull Double latitude,
                                 Double longitude,
                                 @NotNull Instant timeNowForStop);
    void updateInformationInitialVehicleByIdIfSegmentChanged(
             Long currentBusStopId,
             Long nextBusStopId,
            Double latitude,
            Double longitude,
            Integer minWaitTime,
             Integer maxWaitTime,
            Instant currentTimeStoppedForBusStop,
            Long currentSegmentOrder,
            Boolean whereaboutsStatus,
             Boolean resetRoute,
            Instant nextMaxBusStopTimeBusStop,
            Instant nextMinBusStopTimeBusStop,
            Long id
    );

    void updateCompleteRouteById(Long resourceId, boolean isCompleteRoute);
}
