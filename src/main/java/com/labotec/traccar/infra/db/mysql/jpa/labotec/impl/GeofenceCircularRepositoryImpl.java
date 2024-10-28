package com.labotec.traccar.infra.db.mysql.jpa.labotec.impl;

import com.labotec.traccar.app.usecase.ports.input.repository.GeofenceCircularRepository;
import com.labotec.traccar.domain.database.models.CircularGeofence;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.CircularGeofenceEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.GeofenceCircularMapper;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.GeofenceCircularRepositoryJpa;
import com.labotec.traccar.infra.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@AllArgsConstructor
@Repository
public class GeofenceCircularRepositoryImpl implements GeofenceCircularRepository {
    private final GeofenceCircularRepositoryJpa geofenceCircularRepositoryJpa;
    private final GeofenceCircularMapper geofenceCircularMapper;
    @Override
    public CircularGeofence create(CircularGeofence entity) {
        return null;
    }

    @Override
    public CircularGeofence findById(Integer integer) {
        CircularGeofenceEntity circularGeofenceEntity = geofenceCircularRepositoryJpa.findById(Long.valueOf(integer)).orElseThrow(
                () -> new EntityNotFoundException("Geo cerca no encontrada by id " + integer)
        );

        return geofenceCircularMapper.toModel(circularGeofenceEntity);
    }

    @Override
    public Optional<CircularGeofence> findByIdOptional(Integer integer) {
        return Optional.empty();
    }

    @Override
    public Iterable<CircularGeofence> findAll() {
        return null;
    }

    @Override
    public CircularGeofence update(CircularGeofence entity) {
        return null;
    }

    @Override
    public void deleteById(Integer integer) {

    }
}
