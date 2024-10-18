package com.labotec.traccar.app.services;

import com.labotec.traccar.app.usecase.ports.input.LocationRepository;
import com.labotec.traccar.app.usecase.ports.out.GenericCrudService;
import com.labotec.traccar.domain.database.models.Location;
import com.labotec.traccar.domain.web.dto.LocationDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor

public class LocationImpl implements GenericCrudService<Location ,LocationDTO , Integer> {

    private final LocationRepository locationRepository;
    @Override
    public Location create(LocationDTO entityDto) {
        return locationRepository.create(entityDto);
    }

    @Override
    public Location findById(Integer integer) {
        return locationRepository.findById(integer);
    }

    @Override
    public Iterable<Location> findAll() {
        return locationRepository.findAll();
    }

    @Override
    public Location update(LocationDTO entityDto, Integer integer) {
        return locationRepository.update(entityDto,integer);
    }

    @Override
    public void deleteById(Integer integer) {
        locationRepository.deleteById(integer);
    }
}
