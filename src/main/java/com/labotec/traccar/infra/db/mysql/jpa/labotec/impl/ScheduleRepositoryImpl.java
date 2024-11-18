package com.labotec.traccar.infra.db.mysql.jpa.labotec.impl;

import com.labotec.traccar.app.usecase.ports.input.repository.ScheduleRepository;
import com.labotec.traccar.domain.database.models.Schedule;
import com.labotec.traccar.domain.database.models.Vehicle;
import com.labotec.traccar.domain.enums.STATE;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.*;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.ScheduleMapper;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.VehicleMapper;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.*;
import com.labotec.traccar.infra.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static com.labotec.traccar.infra.db.mysql.jpa.labotec.message.ScheduleMessage.SCHEDULE_NOT_FOUND_BY_ID;
import static com.labotec.traccar.infra.db.mysql.jpa.labotec.message.VehicleMessage.VEHICLE_NOT_FOUND_BY_ID;

@AllArgsConstructor
@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {

    private final ScheduleRepositoryJpa scheduleRepositoryJpa;
    private final ScheduleMapper scheduleMapper;
    private final VehicleMapper vehicleMapper;

    @Override
    public Schedule create(Schedule entity) {
        ScheduleEntity schedule = scheduleMapper.toEntity(entity);
        ScheduleEntity scheduleEntitySaved = scheduleRepositoryJpa.save(schedule);
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

}