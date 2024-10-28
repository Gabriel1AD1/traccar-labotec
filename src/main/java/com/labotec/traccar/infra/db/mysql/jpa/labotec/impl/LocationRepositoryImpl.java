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
    public Location findById(Integer id) {
        LocationEntity locationEntity = locationRepositoryJpa.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(LOCATION_NOT_FOUND_BY_ID + id));
        return locationMapper.toModel(locationEntity);
    }

    @Override
    public Optional<Location> findByIdOptional(Integer id) {
        return locationRepositoryJpa.findById(id)
                .map(locationMapper::toModel); // Usar el mapper para convertir de entidad a modelo
    }

    @Override
    public List<Location> findAll() {
        List<LocationEntity> locationEntities = locationRepositoryJpa.findAll();
        return locationMapper.toModelList(locationEntities); // Usar el mapper para convertir la lista de entidades
    }

    @Override
    public Location update(Location entity) {
        LocationEntity locationEntity = locationMapper.toEntity(entity);
        LocationEntity locationSaved = locationRepositoryJpa.save(locationEntity);
        return locationMapper.toModel(locationSaved);
    }

    @Override
    public void deleteById(Integer id) {
        locationRepositoryJpa.deleteById(id);
    }
}
