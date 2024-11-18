package com.labotec.traccar.infra.db.mysql.jpa.labotec.impl;

import com.labotec.traccar.app.usecase.ports.input.repository.LocationRepository;
import com.labotec.traccar.domain.database.models.Location;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.LocationEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.LocationMapper;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.LocationRepositoryJpa;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.labotec.traccar.infra.db.mysql.jpa.labotec.message.LocationMessage.LOCATION_NOT_FOUND_BY_ID;

@AllArgsConstructor
@Repository
public class LocationRepositoryImpl implements LocationRepository {
    private final LocationMapper locationMapper;
    private final LocationRepositoryJpa locationRepositoryJpa;
    @Override
    public Location create(Location entity) {
        LocationEntity locationEntity = locationMapper.toEntity(entity);
        LocationEntity locationSaved = locationRepositoryJpa.save(locationEntity);
        return locationMapper.toModel(locationSaved);    }

    @Override
    public Location findById(Long resourceId, Long userId) {
        LocationEntity locationEntity = locationRepositoryJpa.findByIdAndUserId(resourceId,userId).orElseThrow(
                () -> new EntityNotFoundException("Local no encontrado")
        );
        return locationMapper.toModel(locationEntity);
    }

    @Override
    public Optional<Location> findByIdOptional(Long resourceId, Long userId) {
        LocationEntity locationEntity = locationRepositoryJpa.findByIdAndUserId(resourceId,userId).get();
        return Optional.ofNullable(locationMapper.toModel(locationEntity));
    }

    @Override
    public Iterable<Location> findAll(Long userId) {
        List<LocationEntity> locationEntityList = locationRepositoryJpa.findAllByUserId(userId);
        return locationMapper.toModelList(locationEntityList);
    }

    @Override
    public Location update(Location entity) {
        LocationEntity locationEntity = locationMapper.toEntity(entity);
        LocationEntity locationSaved = locationRepositoryJpa.save(locationEntity);
        return locationMapper.toModel(locationSaved);
    }

    @Override
    public void deleteById(Long resourceId, Long userId) {
        locationRepositoryJpa.deleteByIdAndUserId(resourceId, userId);
    }
}
