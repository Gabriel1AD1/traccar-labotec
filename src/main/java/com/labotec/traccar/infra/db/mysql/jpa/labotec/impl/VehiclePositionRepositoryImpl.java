package com.labotec.traccar.infra.db.mysql.jpa.labotec.impl;

import com.labotec.traccar.app.implementation.RouteProcess;
import com.labotec.traccar.app.ports.input.repository.VehiclePositionRepository;
import com.labotec.traccar.domain.database.models.VehiclePosition;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.VehiclePositionEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.VehiclePositionMapper;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.VehiclePositionRepositoryJpa;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
@AllArgsConstructor
public class VehiclePositionRepositoryImpl implements VehiclePositionRepository {

    private final VehiclePositionMapper vehiclePositionMapper;
    private final VehiclePositionRepositoryJpa vehiclePositionRepositoryJpa;
    private static final Logger logger = LoggerFactory.getLogger(VehiclePositionRepositoryImpl.class);

    @Override
    public VehiclePosition findById(long id) {
        logger.debug("Buscando VehiclePosition con ID: {}", id);
        VehiclePositionEntity entity = vehiclePositionRepositoryJpa.findById(id).orElse(null);
        if (entity == null) {
            logger.warn("No se encontró ningún VehiclePosition con ID: {}", id);
        }
        return vehiclePositionMapper.toDomain(entity);
    }

    @Override
    public VehiclePosition save(VehiclePosition vehiclePosition) {
        logger.debug("Guardando VehiclePosition: {}", vehiclePosition);
        VehiclePositionEntity positionEntity = vehiclePositionMapper.toEntity(vehiclePosition);
        VehiclePositionEntity savedEntity = vehiclePositionRepositoryJpa.save(positionEntity);
        logger.info("VehiclePosition guardado con éxito: {}", savedEntity);
        return vehiclePositionMapper.toDomain(savedEntity);
    }

    public VehiclePosition findByScheduleId(Long scheduleId) {
        logger.debug("Buscando VehiclePosition con Schedule ID: {}", scheduleId);
        VehiclePositionEntity entity = vehiclePositionRepositoryJpa.findByScheduleId(scheduleId);
        if (entity == null) {
            logger.warn("No se encontró ningún VehiclePosition con Schedule ID: {}", scheduleId);
        }
        return vehiclePositionMapper.toDomain(entity);
    }

    @Override
    public void updateTimeStopByRouteId(Long scheduleId, Double latitude, Double longitude, Instant timeNowForStop) {
        logger.debug("Actualizando tiempo detenido para Schedule ID: {}, Latitud: {}, Longitud: {}, Tiempo: {}",
                scheduleId, latitude, longitude, timeNowForStop);
        int rawAffected = vehiclePositionRepositoryJpa.updateStoppedTimeIfLocationChanged(
                timeNowForStop,
                scheduleId,
                latitude,
                longitude);
        if (rawAffected > 0) {
            logger.info("Tiempo detenido actualizado correctamente para Schedule ID: {}", scheduleId);
        } else {
            logger.warn("No se actualizó el tiempo detenido para Schedule ID: {}", scheduleId);
        }
    }

    @Override
    public void updateInformationInitialVehicleByIdIfSegmentChanged(
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
            Long id) {

        logger.debug("Actualizando información inicial del vehículo para ID: {} con Segmento: {}", id, currentSegmentOrder);

        int rawAffected = vehiclePositionRepositoryJpa.updateInformationInitialVehicleByIdIfSegmentChanged(
                currentBusStopId,
                nextBusStopId,
                latitude,
                longitude,
                minWaitTime,
                maxWaitTime,
                currentTimeStoppedForBusStop,
                currentSegmentOrder,
                whereaboutsStatus,
                resetRoute,
                nextMaxBusStopTimeBusStop,
                nextMinBusStopTimeBusStop,
                id);

        if (rawAffected > 0) {
            logger.info("Información inicial actualizada correctamente para ID: {}", id);
        } else {
            logger.warn("No se actualizó la información inicial para ID: {}", id);
        }
    }

    @Override
    public void updateCompleteRouteById(Long resourceId, boolean isCompleteRoute) {
        logger.debug("Actualizando ruta completada para ID: {}, Estado: {}", resourceId, isCompleteRoute);
        int rawAffected = vehiclePositionRepositoryJpa.updateCompleteRouteById(resourceId, isCompleteRoute);
        if (rawAffected > 0) {
            logger.info("Ruta completada actualizada correctamente para ID: {}", resourceId);
        } else {
            logger.warn("No se actualizó la ruta completada para ID: {}", resourceId);
        }
    }
}

