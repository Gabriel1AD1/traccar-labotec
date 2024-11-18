package com.labotec.traccar.infra.db.mysql.jpa.labotec.impl;

import com.labotec.traccar.app.usecase.ports.input.repository.GeofenceCircularRepository;
import com.labotec.traccar.domain.database.models.CircularGeofence;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.CircularGeofenceEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.GeofenceCircularMapper;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.GeofenceCircularRepositoryJpa;
import com.labotec.traccar.infra.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class GeofenceCircularRepositoryImpl implements GeofenceCircularRepository {
    private final GeofenceCircularRepositoryJpa geofenceCircularRepositoryJpa;
    private final GeofenceCircularMapper geofenceCircularMapper;
    @Override
    public CircularGeofence create(CircularGeofence entity) {
        CircularGeofenceEntity circularGeofenceEntityMap = geofenceCircularMapper.toEntity(entity);
        CircularGeofenceEntity circularGeofenceSaved = geofenceCircularRepositoryJpa.save(circularGeofenceEntityMap);
        return geofenceCircularMapper.toModel(circularGeofenceSaved);
    }

    @Override
    public CircularGeofence findById(Long resourceId, Long userId) {
        CircularGeofenceEntity circularGeofence = geofenceCircularRepositoryJpa.findByIdAndUserId(resourceId,userId).orElseThrow(
                ()-> new EntityNotFoundException("Geo cerca no encontrada")
        );
        return geofenceCircularMapper.toModel(circularGeofence);
    }

    @Override
    public Optional<CircularGeofence> findByIdOptional(Long resourceId, Long userId) {
        CircularGeofenceEntity circularGeofence = geofenceCircularRepositoryJpa.findByIdAndUserId(resourceId,userId).get();
        CircularGeofence circularGeofenceMap = geofenceCircularMapper.toModel(circularGeofence);
        return Optional.ofNullable(circularGeofenceMap);
    }

    @Override
    public Iterable<CircularGeofence> findAll(Long userId) {
        List<CircularGeofenceEntity> circularGeofenceEntityList = geofenceCircularRepositoryJpa.findAllByUserId(userId);
        return geofenceCircularMapper.toModelList(circularGeofenceEntityList);
    }


    @Override
    public CircularGeofence update(CircularGeofence entity) {
        CircularGeofenceEntity circularGeofenceEntityMap = geofenceCircularMapper.toEntity(entity);
        CircularGeofenceEntity circularGeofenceSaved = geofenceCircularRepositoryJpa.save(circularGeofenceEntityMap);
        return geofenceCircularMapper.toModel(circularGeofenceSaved);
    }

    @Override
    public void deleteById(Long resourceId, Long userId) {
        geofenceCircularRepositoryJpa.deleteByIdAndUserId(resourceId,userId);
    }
}
