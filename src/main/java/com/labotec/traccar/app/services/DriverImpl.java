package com.labotec.traccar.app.services;

import com.labotec.traccar.app.usecase.ports.input.DriverRepository;
import com.labotec.traccar.app.usecase.ports.out.GenericCrudService;
import com.labotec.traccar.domain.database.models.Driver;
import com.labotec.traccar.domain.web.dto.DriverDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor

public class DriverImpl implements GenericCrudService<Driver , DriverDTO , Integer> {

    private final DriverRepository driverRepository ;
    @Override
    public Driver create(DriverDTO entityDto) {
        return driverRepository.create(entityDto);
    }

    @Override
    public Driver findById(Integer integer) {
        return driverRepository.findById(integer);
    }

    @Override
    public Iterable<Driver> findAll() {
        return driverRepository.findAll();
    }

    @Override
    public Driver update(DriverDTO entityDto, Integer integer) {
        return driverRepository.update(entityDto,integer);
    }

    @Override
    public void deleteById(Integer integer) {
        driverRepository.deleteById(integer);
    }
}
