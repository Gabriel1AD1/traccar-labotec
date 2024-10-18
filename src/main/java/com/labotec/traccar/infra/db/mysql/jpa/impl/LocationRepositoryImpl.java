package com.labotec.traccar.infra.db.mysql.jpa.impl;

import com.labotec.traccar.app.usecase.ports.input.LocationRepository;
import com.labotec.traccar.domain.database.models.Location;
import com.labotec.traccar.domain.web.dto.LocationDTO;
import com.labotec.traccar.infra.db.mysql.jpa.entity.CompanyEntity;
import com.labotec.traccar.infra.db.mysql.jpa.entity.LocationEntity;
import com.labotec.traccar.infra.db.mysql.jpa.mapper.LocationMapper;
import com.labotec.traccar.infra.db.mysql.jpa.repository.CompanyRepositoryJpa;
import com.labotec.traccar.infra.db.mysql.jpa.repository.LocationRepositoryJpa;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.labotec.traccar.infra.db.mysql.jpa.message.ComapanyMessage.COMPANY_NOT_FOUND_BY_ID;
import static com.labotec.traccar.infra.db.mysql.jpa.message.LocationMessage.LOCATION_NOT_FOUND_BY_ID;

@AllArgsConstructor
@Repository
public class LocationRepositoryImpl implements LocationRepository {
    private final LocationMapper locationMapper;
    private final LocationRepositoryJpa locationRepositoryJpa;
    private final CompanyRepositoryJpa companyRepositoryJpa;

    @Override
    public Location create(LocationDTO entity) {
        // Buscar la compañía asociada
        CompanyEntity company = companyRepositoryJpa.findById(entity.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException(COMPANY_NOT_FOUND_BY_ID + entity.getCompanyId()));

        // Crear una entidad LocationEntity manualmente a partir del DTO
        LocationEntity locationEntity = new LocationEntity();
        locationEntity.setName(entity.getName());
        locationEntity.setLatitude(entity.getLatitude());
        locationEntity.setLongitude(entity.getLongitude());
        locationEntity.setRadius(entity.getRadius());
        locationEntity.setCompany(company); // Asignar la compañía asociada

        // Guardar la entidad en la base de datos
        LocationEntity savedLocation = locationRepositoryJpa.save(locationEntity);

        // Devolver el modelo de dominio Location usando el LocationMapper
        return locationMapper.toModel(savedLocation);
    }

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
    public Location update(LocationDTO entity, Integer id) {
        // Buscar la entidad LocationEntity por su ID
        LocationEntity locationEntity = locationRepositoryJpa.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(LOCATION_NOT_FOUND_BY_ID + id));

        // Actualizar manualmente los campos con los valores del DTO
        locationEntity.setName(entity.getName());
        locationEntity.setLatitude(entity.getLatitude());
        locationEntity.setLongitude(entity.getLongitude());
        locationEntity.setRadius(entity.getRadius());

        // Buscar la compañía asociada y asignarla
        CompanyEntity company = companyRepositoryJpa.findById(entity.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException(COMPANY_NOT_FOUND_BY_ID + entity.getCompanyId()));
        locationEntity.setCompany(company);

        // Guardar la entidad actualizada en la base de datos
        LocationEntity updatedLocation = locationRepositoryJpa.save(locationEntity);

        // Devolver el modelo de dominio actualizado usando el mapper
        return locationMapper.toModel(updatedLocation);
    }

    @Override
    public void deleteById(Integer id) {
        locationRepositoryJpa.deleteById(id);
    }
}
