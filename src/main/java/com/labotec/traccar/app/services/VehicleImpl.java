package com.labotec.traccar.app.services;

import com.labotec.traccar.app.usecase.ports.input.VehicleRepository;
import com.labotec.traccar.app.usecase.ports.out.GenericCrudService;
import com.labotec.traccar.domain.database.models.Vehicle;
import com.labotec.traccar.domain.web.dto.VehicleDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor

public class VehicleImpl  implements GenericCrudService<Vehicle,VehicleDTO , Integer> {
    private final VehicleRepository vehicleRepository;
    @Override
    public Vehicle create(VehicleDTO entityDto) {
        return vehicleRepository.create(entityDto);
    }

    @Override
    public Vehicle findById(Integer integer) {
        return vehicleRepository.findById(integer);
    }

    @Override
    public Iterable<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    @Override
    public Vehicle update(VehicleDTO entityDto, Integer integer) {
        return vehicleRepository.update(entityDto,integer);
    }

    @Override
    public void deleteById(Integer integer) {
        vehicleRepository.deleteById(integer);
    }
}
