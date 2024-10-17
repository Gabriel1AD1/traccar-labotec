package com.labotec.traccar.app.services;

import com.labotec.traccar.app.usecase.ports.input.RouteRepository;
import com.labotec.traccar.app.usecase.ports.out.GenericCrudService;
import com.labotec.traccar.domain.database.models.Route;
import com.labotec.traccar.domain.web.dto.RouteDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor

public class RouteImpl implements GenericCrudService<Route,RouteDTO , Integer> {
    private final RouteRepository routeRepository;
    @Override
    public Route create(RouteDTO entityDto) {
        return routeRepository.create(entityDto);
    }

    @Override
    public Route findById(Integer aLong) {
        return routeRepository.findById(aLong);
    }

    @Override
    public Iterable<Route> findAll() {
        return routeRepository.findAll();
    }

    @Override
    public Route update(RouteDTO entityDto, Integer aLong) {
        return routeRepository.update(entityDto,aLong);
    }

    @Override
    public void deleteById(Integer aLong) {
        routeRepository.deleteById(aLong);
    }
}
