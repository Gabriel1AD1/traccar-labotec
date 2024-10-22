package com.labotec.traccar.app.implemenatition;

import com.labotec.traccar.app.mapper.RouteBusStopModelMapper;
import com.labotec.traccar.app.usecase.ports.input.BusStopRepository;
import com.labotec.traccar.app.usecase.ports.input.RouteBusStopRepository;
import com.labotec.traccar.app.usecase.ports.input.RouteRepository;
import com.labotec.traccar.app.usecase.ports.out.RouteBusStopService;
import com.labotec.traccar.domain.database.models.BusStop;
import com.labotec.traccar.domain.database.models.Route;
import com.labotec.traccar.domain.database.models.RouteBusStop;
import com.labotec.traccar.domain.web.dto.RouteBusStopDTO;
import com.labotec.traccar.domain.web.dto.RouteBusUpdateStopDTO;
import com.labotec.traccar.domain.web.dto.RouteDTO;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
//TODO AREGLAR LOGICA
public class RouteBusStopImpl implements RouteBusStopService {
    private final BusStopRepository busStopRepository ;
    private final RouteRepository routeRepository;
    private final RouteBusStopRepository routeBusStopRepository;
    private final RouteBusStopModelMapper routeBusStopModelMapper;
    @Override
    // TODO Recivir una lista de routeBUstop
    public Iterable<RouteBusStop> createList(RouteBusStopDTO entityDto) {
        Route routeFind = routeRepository.findById(entityDto.getRouteId());
        List<RouteBusStop> routeBusStops = new ArrayList<>();

        for(RouteDTO.BusStopOrderDTO routeIterable : entityDto.getBusStops()){
            RouteBusStop routeBusStopCreate = new RouteBusStop();
            BusStop busStopFind = busStopRepository.findById(routeIterable.getBusStopId());
            routeBusStopCreate.setBusStop(busStopFind);
            routeBusStopCreate.setOrder(routeBusStopCreate.getOrder());
            routeBusStopCreate.setRoute(routeFind);
            routeBusStops.add(routeBusStopCreate);
        }

        return routeBusStopRepository.createList(routeBusStops);
    }

    @Override
    public Iterable<RouteBusStop> updateToList(RouteBusUpdateStopDTO entityDto) {
        Route routeFind = routeRepository.findById(entityDto.getRouteId());
        List<RouteBusStop> routeBusStops = new ArrayList<>();

        for(RouteBusUpdateStopDTO.BusStopOrderUpdateDTO routeIterable : entityDto.getBusStops()){
            RouteBusStop routeBusStopCreate = routeBusStopRepository.findById(routeIterable.getId());
            BusStop busStopFind = busStopRepository.findById(routeIterable.getBusStopId());
            routeBusStopCreate.setBusStop(busStopFind);
            routeBusStopCreate.setOrder(routeBusStopCreate.getOrder());
            routeBusStopCreate.setRoute(routeFind);
            routeBusStops.add(routeBusStopCreate);
        }

        return routeBusStopRepository.createList(routeBusStops);
    }

    @Override
    @Deprecated
    //TODO DEVOLVE OBJETO
    public RouteBusStop create(RouteBusStopDTO entityDto) {
        return null;
    }

    @Override
    public RouteBusStop findById(Integer integer) {
        return routeBusStopRepository.findById(integer);
    }

    @Override
    public Iterable<RouteBusStop> findAll() {
        return routeBusStopRepository.findAll();
    }

    @Override
    @Deprecated
    //TODO DEVOLVER NULL
    public RouteBusStop update(RouteBusStopDTO entityDto, Integer integer) {
        return null;
    }
    @Override
    public void deleteById(Integer integer) {
        routeBusStopRepository.deleteById(integer);
    }

    @Override
    public Iterable<RouteBusStop> findByRoute(Integer routeId) {
        return routeBusStopRepository.findByRoute(routeId);
    }
}
