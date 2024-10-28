package com.labotec.traccar.infra.db.mysql.jpa.labotec.impl;

import com.labotec.traccar.app.usecase.ports.input.repository.ScheduleRepository;
import com.labotec.traccar.domain.database.models.Schedule;
import com.labotec.traccar.domain.database.models.Vehicle;
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
    public Schedule findById(Integer id) {
        ScheduleEntity scheduleEntity = scheduleRepositoryJpa.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(SCHEDULE_NOT_FOUND_BY_ID + id));
        return scheduleMapper.toModel(scheduleEntity);
    }

    @Override
    public Optional<Schedule> findByIdOptional(Integer id) {
        return scheduleRepositoryJpa.findById(id)
                .map(scheduleMapper::toModel); // Usar el mapper para convertir de entidad a modelo
    }

    @Override
    public List<Schedule> findAll() {
        List<ScheduleEntity> scheduleEntities = scheduleRepositoryJpa.findAll();
        return scheduleMapper.toModelList(scheduleEntities); // Usar el mapper para convertir la lista de entidades
    }

    @Override
    public Schedule update(Schedule entity) {
        ScheduleEntity schedule = scheduleMapper.toEntity(entity);
        ScheduleEntity scheduleEntitySaved = scheduleRepositoryJpa.save(schedule);
        return scheduleMapper.toModel(scheduleEntitySaved);    }



    @Override
    public void deleteById(Integer id) {
        scheduleRepositoryJpa.deleteById(id);
    }

    @Override
    public Schedule updateStatus(Integer id, Byte status) {
        ScheduleEntity scheduleEntity = scheduleRepositoryJpa.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(SCHEDULE_NOT_FOUND_BY_ID + id)
        );
        ScheduleEntity scheduleSaved = scheduleRepositoryJpa.save(scheduleEntity);
        return scheduleMapper.toModel(scheduleSaved);
    }

    @Override
    public List<Schedule> findAllByDateRange(Instant from, Instant to) {
        List<ScheduleEntity> list = scheduleRepositoryJpa.findAllByDepartureTimeBetween(from , to);
        return scheduleMapper.toModelList(list);
    }

    @Override
    public Schedule updateEstimatedDepartureTime(Integer id, Instant estimatedDepartureTime) {
        ScheduleEntity scheduleEntity = scheduleRepositoryJpa.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(SCHEDULE_NOT_FOUND_BY_ID + id));

        // Actualizar el campo de estimatedDepartureTime
        scheduleEntity.setEstimatedDepartureTime(estimatedDepartureTime);

        // Guardar la entidad actualizada en la base de datos
        ScheduleEntity updatedSchedule = scheduleRepositoryJpa.save(scheduleEntity);

        // Devolver el modelo de dominio actualizado usando el mapper
        return scheduleMapper.toModel(updatedSchedule);
    }

    @Override
    public Schedule updateEstimatedArrivalTime(Integer id, Instant estimatedArrivalTime) {
        ScheduleEntity scheduleEntity = scheduleRepositoryJpa.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(SCHEDULE_NOT_FOUND_BY_ID + id));

        // Actualizar el campo de estimatedDepartureTime
        scheduleEntity.setArrivalTime(estimatedArrivalTime);

        // Guardar la entidad actualizada en la base de datos
        ScheduleEntity updatedSchedule = scheduleRepositoryJpa.save(scheduleEntity);

        // Devolver el modelo de dominio actualizado usando el mapper
        return scheduleMapper.toModel(updatedSchedule);
    }

    @Override
    public List<Schedule> findByVehicle(Vehicle vehicle) {

        VehicleEntity vehicleFind = vehicleMapper.toEntity(vehicle);
        // Consulta JPA para obtener las programaciones por vehicleId
        List<ScheduleEntity> scheduleEntities = scheduleRepositoryJpa.findByVehicle(vehicleFind);

        // Mapeo de entidades a modelos de dominio
        return scheduleMapper.toModelList(scheduleEntities);    }

    @Override
    public Schedule findByVehicleLastedRegister(Vehicle vehicleId) {
        VehicleEntity vehicleEntity = vehicleMapper.toEntity(vehicleId);
        ScheduleEntity scheduleEntity = scheduleRepositoryJpa.findTopByVehicleOrderByDepartureTimeDesc(vehicleEntity).
                orElseThrow(()-> new EntityNotFoundException(VEHICLE_NOT_FOUND_BY_ID));
        return scheduleMapper.toModel(scheduleEntity);
    }
}
