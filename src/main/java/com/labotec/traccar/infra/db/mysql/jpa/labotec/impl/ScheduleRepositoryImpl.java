package com.labotec.traccar.infra.db.mysql.jpa.labotec.impl;

import com.labotec.traccar.app.ports.input.repository.ScheduleRepository;
import com.labotec.traccar.domain.database.models.Schedule;
import com.labotec.traccar.domain.database.models.ScheduleDelayInformation;
import com.labotec.traccar.domain.database.models.read.InformationRoute;
import com.labotec.traccar.domain.enums.STATE;
import com.labotec.traccar.domain.query.OptimizedSchedule;
import com.labotec.traccar.domain.query.ScheduleProcessPosition;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.ScheduleEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.RouteMapper;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.ScheduleMapper;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.VehicleMapper;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.projection.InformationScheduleProjection;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.projection.OptimizedScheduleProjection;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.projection.ScheduleDelayInformationProjection;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.projection.ScheduleProjection;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.projection.mapper.ScheduleProjectionMapper;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.ScheduleRepositoryJpa;
import com.labotec.traccar.app.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {

    private final ScheduleRepositoryJpa scheduleRepositoryJpa;
    private final ScheduleMapper scheduleMapper;
    private final VehicleMapper vehicleMapper;
    private final RouteMapper routeMapper;
    private final ScheduleProjectionMapper scheduleProjectionMapper;
    private static final Logger logger = LoggerFactory.getLogger(ScheduleRepositoryImpl.class);
    @Override
    public Schedule create(Schedule entity) {
        ScheduleEntity schedule = scheduleMapper.toEntity(entity);
        ScheduleEntity scheduleEntitySaved = scheduleRepositoryJpa.save(schedule);
        System.out.println(scheduleEntitySaved.toString());
        return scheduleMapper.toModel(scheduleEntitySaved);
    }

    @Override
    public Schedule findById(Long resourceId, Long userId) {
        ScheduleEntity scheduleEntity = scheduleRepositoryJpa.findByIdAndUserId_UserId(resourceId,userId).orElseThrow(
                () -> new EntityNotFoundException("La programación buscada no existe")
        );
        return scheduleMapper.toModel(scheduleEntity);
    }

    @Override
    public Optional<Schedule> findByIdOptional(Long resourceId, Long userId) {
        ScheduleEntity scheduleEntity = scheduleRepositoryJpa.findByIdAndUserId_UserId(resourceId,userId).get();
        Schedule schedule = scheduleMapper.toModel(scheduleEntity);
        return Optional.ofNullable(schedule);
    }

    @Override
    public Iterable<Schedule> findAll(Long userId) {
        return scheduleMapper.toModelList(scheduleRepositoryJpa.findAllByUserId_UserId(userId));
    }

    @Override
    public Schedule update(Schedule entity) {
        ScheduleEntity schedule = scheduleMapper.toEntity(entity);
        ScheduleEntity scheduleEntitySaved = scheduleRepositoryJpa.save(schedule);
        return scheduleMapper.toModel(scheduleEntitySaved);    }

    @Override
    public void deleteById(Long resourceId, Long userId) {
        scheduleRepositoryJpa.deleteByIdAndUserId_UserId(resourceId,userId);
    }

    @Override
    public void updateStatus(Long resourceId, STATE status, Long userId) {
        scheduleRepositoryJpa.updateStatusByUserIdAndId(status,resourceId,userId);
    }

    @Override
    public List<Schedule> findAllByDateRange(Instant from, Instant to, Long userId) {
        List<ScheduleEntity> findAll = scheduleRepositoryJpa.findAllByDepartureTimeBetweenAndUserId(from, to, userId);
        return scheduleMapper.toModelList(findAll);
    }

    @Override
    public void updateEstimatedDepartureTime(Long resourceId, Instant estimatedDepartureTime, Long userId) {
        scheduleRepositoryJpa.updateEstimatedDepartureTime(resourceId, estimatedDepartureTime, userId);
    }


    @Override
    public void updateEstimatedArrivalTime(Long resourceId, Instant estimatedArrivalTime, Long userId) {
        scheduleRepositoryJpa.updateEstimatedArrivalTime(resourceId, estimatedArrivalTime, userId);
    }

    @Override
    public List<Schedule> findByVehicle(Long vehicleId, Long userId) {
        List<ScheduleEntity> findAll = scheduleRepositoryJpa.findByUserIdAndVehicleId(vehicleId,userId);
        return scheduleMapper.toModelList(findAll);
    }

    @Override
    public Schedule findByVehicleLastedRegister(Long vehicleId, Long userId) {
        ScheduleEntity scheduleEntity = scheduleRepositoryJpa.findTopByVehicleAndUserOrderByDepartureTimeDesc(vehicleId,userId).orElseThrow(
                () -> new jakarta.persistence.EntityNotFoundException("La ultima programación no existe")
        );
        return scheduleMapper.toModel(scheduleEntity);
    }

    @Override
    public Schedule findByVehicleId(Long vehicleId) {
        return null;
    }

    @Override
    public boolean existsOverlappingSchedule(Long vehicleId, Instant newDepartureTime, Instant newArrivalTime) {
        return scheduleRepositoryJpa.existsOverlappingSchedule(vehicleId,newDepartureTime,newArrivalTime);
    }

    @Override
    public Schedule findByVehicleIdAndInstantNow(long deviceId, Instant now) {
        ScheduleEntity schedule = scheduleRepositoryJpa.findScheduleByVehicleAndCurrentTime(deviceId,now).orElseThrow(
                () -> new EntityNotFoundException(" la entidad no existe"));

        return scheduleMapper.toModel(schedule);
    }

    @Override
    @Cacheable(value = "scheduleCache", key = "#deviceId", sync = true)
    public Optional<ScheduleProcessPosition> findByScheduleProjectionVehicleIdAndInstantNow(long deviceId, Instant now) {
        ScheduleProjection scheduleProjection = scheduleRepositoryJpa.findScheduleProjectionViewByVehicleAndCurrentTime(deviceId,now).orElseThrow(
                ()-> new EntityNotFoundException("No hay rutas disponibles para este auto"));
        if (scheduleProjection == null){
            return Optional.empty();
        }
        ScheduleProcessPosition scheduleProcessPosition;
        scheduleProcessPosition  = scheduleProjectionMapper.toScheduleProcessPosition(scheduleProjection);
        return Optional.of(scheduleProcessPosition);

    }

    @Override
    public Optional<Long> findByScheduleVehicleIdAndInstantNow(long deviceId, Instant now) {
        Long scheduleId = scheduleRepositoryJpa.findRouteIdByScheduleForVehicleIdAndCurrentTime(deviceId,now).orElse(null);
        return Optional.ofNullable(scheduleId);
    }

    @Override
    public void updateScheduleForDelay(Long vehicleId, Instant newArrivalTime, int totalAdditionalMinutes) {
    }


    @Override
    public void updateDepartureTime(Long id, Instant now) {
        scheduleRepositoryJpa.updateDepartureTimeById(id, now);
    }

    @Override
    public void updateArrivedTime(Long id, Instant now) {
        scheduleRepositoryJpa.updateArrivalTimeById(id, now);
    }

    @Override
    public void updateEstimatedArrivalTimeByVehicleAndCurrentTime(Long deviceId, Instant currentTime, Instant newArrivalTime) {
        int rowAffected = scheduleRepositoryJpa.updateEstimatedArrivalTimeByVehicleAndCurrentTime(deviceId,currentTime,newArrivalTime);
        if(rowAffected > 0 ){
            logger.info("Se ha actualizado correctamente la programacion");
        }else {
            logger.warn("No se ha actualizado correctamente la programacion");
        }
    }

    @Override
    public void updateProgramCompletionStatus(Long resourceId, Boolean isComplete) {
        int rowAffected = scheduleRepositoryJpa.updateProgramCompletionStatus(resourceId,isComplete);
        if(rowAffected > 0 ){
            logger.info("Se ha actualizado correctamente la programacion con el id {} ", resourceId);
        }else {
            logger.warn("No se ha actualizado correctamente la programacion a estado false");
        }
    }

    @Override
    public void updateEstimatedArrivedTime(Long id, Instant now) {
        int arrowAffected = scheduleRepositoryJpa.updateEstimatedArrivalTimeByScheduleId(id, now);
        if(arrowAffected > 0 ){
            logger.info("Se ha actualizado correctamente el campo de estimation de tiempo de llegada la programacion con el id {} ", id);
        }else {
            logger.warn("No se ha actualizado correctamente el campo de tiempo de llegada");
        }
    }

    @Override
    public OptimizedSchedule findDeviceIdAndCurrentTime(Long deviceId, Instant currentTime) {
        OptimizedScheduleProjection optimizedScheduleProjection  = scheduleRepositoryJpa.findOptimizedScheduleById(deviceId,currentTime).orElseThrow(
                () -> new EntityNotFoundException("La programacion del vehiculo: "+ deviceId + " No se encontro")
        );
        return OptimizedSchedule.builder()
                .state(optimizedScheduleProjection.getStatus())
                .geofenceId(optimizedScheduleProjection.getGeofenceId())
                .radiusValidatePolyline(optimizedScheduleProjection.getRadiusValidateRoutePolyline())
                .validateRouteExplicit(optimizedScheduleProjection.getValidateRouteExplicit())
                .typeGeofence(optimizedScheduleProjection.getGeofenceType())
                .build();
    }

    @Override
    public List<ScheduleDelayInformation> findAllSchedulesForDelay(Long vehicleId,Instant arrivedTime) {
        List<ScheduleDelayInformationProjection> findAll= scheduleRepositoryJpa.findSchedulesByVehicleAndTimeGreaterThan(vehicleId,arrivedTime);
        List<ScheduleDelayInformation> response = new ArrayList<>();
        findAll.forEach(s -> response.add(ScheduleDelayInformation.builder()
                .id(s.getId())
                .estimatedDepartureTime(s.getEstimatedDepartureTime())
                .estimatedArrivalTime(s.getEstimatedArrivalTime()).build())
        );
        return response;
    }

    @Override
    public void updateScheduleTimesById(Long id, Instant estimatedDepartureTime, Instant estimatedArrivalTime) {
        int rowAffected = scheduleRepositoryJpa.updateScheduleTimesById(id,estimatedDepartureTime,estimatedArrivalTime);
        if(rowAffected > 0 ){
            logger.info("Se ha actualizado correctamente la programacion con el id {} ", id);
        }else {
            logger.warn("No se ha actualizado correctamente la programacion");
        }
    }

    @Override
    public void updateCompletionPercentageByScheduleId(Long scheduleId, Double percentageCompleted) {
        int arrowAffected = scheduleRepositoryJpa.updatePercentageCompletedByScheduleId(scheduleId,percentageCompleted);
        if (arrowAffected > 0){
            logger.info("El porcentaje de la programacion se ha actualizado en un {}",percentageCompleted);
        }else {
            logger.info("No se ha podido actualizar el porcentaje dela programacion");
        }
    }

    @Override
    public Optional<InformationRoute> findByInformationSchedule(long vehicleId, Instant now){
        InformationScheduleProjection scheduleInformation = scheduleRepositoryJpa.findByInformationScheduleIds(vehicleId,now).orElse(null);
        if (scheduleInformation == null){
            return Optional.empty();
        }
        InformationRoute informationRoute = new InformationRoute();
        informationRoute.setUserId(scheduleInformation.getUserId());
        informationRoute.setEstimatedArrivedTime(scheduleInformation.getEstimatedArrivalTime());
        informationRoute.setEstimatedDepartureTime(scheduleInformation.getEstimatedDepartureTime());
        informationRoute.setScheduleId(scheduleInformation.getScheduleId());
        informationRoute.setRouteId(scheduleInformation.getRouteId());
        return Optional.of(informationRoute);


    }
}
