package com.labotec.traccar.app.implementation;

import com.labotec.traccar.app.mapper.RouteBusStopModelMapper;
import com.labotec.traccar.app.usecase.ports.input.repository.BusStopRepository;
import com.labotec.traccar.app.usecase.ports.input.repository.RouteBusStopRepository;
import com.labotec.traccar.app.usecase.ports.input.repository.RouteRepository;
import com.labotec.traccar.app.usecase.ports.out.RouteBusStopService;
import com.labotec.traccar.domain.database.models.BusStop;
import com.labotec.traccar.domain.database.models.Route;
import com.labotec.traccar.domain.database.models.RouteBusStop;
import com.labotec.traccar.domain.web.dto.entel.create.RouteBusStopDTO;
import com.labotec.traccar.domain.web.dto.entel.create.RouteBusStopIterableDTO;
import com.labotec.traccar.domain.web.dto.entel.update.RouteBusStopUpdateDTO;
import com.labotec.traccar.domain.web.dto.entel.update.RouteBusUpdateStopIterableDTO;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class RouteBusStopImpl implements RouteBusStopService {
    private final BusStopRepository busStopRepository ;
    private final RouteRepository routeRepository;
    private final RouteBusStopRepository routeBusStopRepository;
    private final RouteBusStopModelMapper routeBusStopModelMapper;

    @Override
    public Iterable<RouteBusStop> findByRoute(Integer routeId) {
        Route route = routeRepository.findById(routeId);
        return routeBusStopRepository.findByRoute(route);
    }

    @Override
    public Iterable<RouteBusStop> createList(RouteBusStopIterableDTO entityDto) {
        Route routeFind = routeRepository.findById(entityDto.getRouteId());
        List<RouteBusStop> routeBusStops = new ArrayList<>();

        for(RouteBusStopIterableDTO.BusStopOrderDTO routeIterable : entityDto.getBusStops()){
            RouteBusStop routeBusStopCreate = new RouteBusStop();
            BusStop firstBusStopFind = busStopRepository.findById(routeIterable.getFirstBusStopId());
            BusStop secondBusStopFind = busStopRepository.findById(routeIterable.getSecondBusStopId());
            routeBusStopCreate.setFirstBusStop(firstBusStopFind);
            routeBusStopCreate.setSecondBusStop(secondBusStopFind);
            routeBusStopCreate.setOrder(routeBusStopCreate.getOrder());
            routeBusStopCreate.setRoute(routeFind);
            routeBusStops.add(routeBusStopCreate);
        }

        return routeBusStopRepository.createList(routeBusStops);
    }

    @Override
    public Iterable<RouteBusStop> updateToList(RouteBusUpdateStopIterableDTO entityDto) {
        Route routeFind = routeRepository.findById(entityDto.getRouteId());
        List<RouteBusStop> routeBusStops = new ArrayList<>();

        for(RouteBusUpdateStopIterableDTO.BusStopOrderUpdateDTO routeIterable : entityDto.getBusStops()){
            RouteBusStop routeBusStopCreate = routeBusStopRepository.findById(routeIterable.getId());
            BusStop firstBusStopFind = busStopRepository.findById(routeIterable.getFirstBusStopId());
            BusStop secondBusStopFind = busStopRepository.findById(routeIterable.getSecondBusStopId());
            routeBusStopCreate.setFirstBusStop(firstBusStopFind);
            routeBusStopCreate.setSecondBusStop(secondBusStopFind);
            routeBusStopCreate.setOrder(routeBusStopCreate.getOrder());
            routeBusStopCreate.setRoute(routeFind);
            routeBusStops.add(routeBusStopCreate);
        }

        return routeBusStopRepository.createList(routeBusStops);
    }

    @Override
    public RouteBusStop create(RouteBusStopDTO entityDto) {
        RouteBusStop routeBusStop = routeBusStopModelMapper.toRouteBusStop(entityDto);
        Route route = routeRepository.findById(entityDto.getRouteId());
        BusStop firstBusStop = busStopRepository.findById(entityDto.getFirstBusStopId());
        BusStop  secondBuStop = busStopRepository.findById(entityDto.getSecondBusStopId());
        routeBusStop.setRoute(route);
        routeBusStop.setFirstBusStop(firstBusStop);
        routeBusStop.setSecondBusStop(secondBuStop);
        return routeBusStopRepository.create(routeBusStop);
    }
    @Override
    public RouteBusStop update(RouteBusStopUpdateDTO routeBusStop, Integer id) {
        RouteBusStop routeBusStopFind  = routeBusStopRepository.findById(id);
        Route route = routeRepository.findById(id);
        BusStop firstBusStop = busStopRepository.findById(routeBusStop.getFirstBusStopId());
        BusStop  secondBuStop = busStopRepository.findById(routeBusStop.getSecondBusStopId());
        routeBusStopFind.setRoute(route);
        routeBusStopFind.setFirstBusStop(firstBusStop);
        routeBusStopFind.setSecondBusStop(secondBuStop);
        routeBusStopFind.setOrder(routeBusStop.getOrder());
        return routeBusStopRepository.create(routeBusStopFind);
    }


    @Override
    public RouteBusStop findById(Integer integer) {
        return routeBusStopRepository.findById(integer);
    }

    @Override
    public Iterable<RouteBusStop> findAll() {
        return routeBusStopRepository.findAll();
    }



    public void deleteById(Integer integer) {
        routeBusStopRepository.deleteById(integer);
    }

}
