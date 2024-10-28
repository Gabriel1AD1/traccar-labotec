package com.labotec.traccar.app.implementation;

import com.labotec.traccar.app.mapper.RouteModelMapper;
import com.labotec.traccar.app.usecase.ports.input.repository.*;
import com.labotec.traccar.app.usecase.ports.out.RouteService;
import com.labotec.traccar.domain.database.models.*;
import com.labotec.traccar.domain.web.dto.create.RouteDTO;
import com.labotec.traccar.domain.web.dto.update.RouteUpdateDTO;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class RouteImpl implements RouteService
{
    private final RouteRepository routeRepository;
    private final CompanyRepository companyRepository;
    private final BusStopRepository busStopRepository;
    private final RouteBusStopRepository routeBusStopRepository;
    private final RouteModelMapper routeModelMapper;
    private final OverviewPolylineRepository overviewPolylineRepository;
    private final VehicleRepository vehicleRepository;
    private final ScheduleRepository scheduleRepository;
    @Override
    public Route create(RouteDTO entityDto) {
        Company company = companyRepository.findById(entityDto.getCompanyId());
        BusStop originBusStop  = busStopRepository.findById(entityDto.getOriginBusStopId());
        BusStop destinationBusStop = busStopRepository.findById(entityDto.getDestinationBusStopId());
        Route route = routeModelMapper.toRoute(entityDto);
        route.setCompany(company);
        route.setOriginBusStop(originBusStop);
        route.setDestinationBusStop(destinationBusStop);
        Route routeSave = routeRepository.create(route);
        for(RouteDTO.BusStopDTO routeIterable : entityDto.getBusStops()){
            RouteBusStop routeBusStopCreate = new RouteBusStop();
            BusStop firstBusStop = busStopRepository.findById(routeIterable.getFirstBusStopId());
            BusStop secondBusStop = busStopRepository.findById(routeIterable.getSecondBusStopId());
            routeBusStopCreate.setFirstBusStop(firstBusStop);
            routeBusStopCreate.setSecondBusStop(secondBusStop);
            routeBusStopCreate.setOrder(routeIterable.getOrder());
            System.out.println("Order: " + routeBusStopCreate.getOrder());

            routeBusStopCreate.setRoute(routeSave);
            routeBusStopCreate.setCompleted(false);

            System.out.println(routeBusStopCreate.toString());

            RouteBusStop routeBusStopSaved = routeBusStopRepository.create(routeBusStopCreate);
            for (RouteDTO.BusStopDTO busStopDTO : entityDto.getBusStops()) {
                for(RouteDTO.BusStopDTO.PolylineDTO polylineDTO : busStopDTO.getPolylines()){
                    OverviewPolyline overviewPolyline = new OverviewPolyline();
                    overviewPolyline.setRoute(routeSave);
                    overviewPolyline.setPolyline(polylineDTO.getPolyline());
                    overviewPolyline.setRouteBusStop(routeBusStopSaved);
                    overviewPolyline.setIsPrimary(polylineDTO.getIsPrimary());
                    overviewPolylineRepository.create(overviewPolyline);

                }

            }
        }


        return routeSave;
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
    public Route update(RouteUpdateDTO entityDto, Integer aLong) {
        Route routing = routeRepository.findById(aLong);
        Company company = companyRepository.findById(entityDto.getCompanyId());
        BusStop originBusStop = busStopRepository.findById(entityDto.getOriginBusStopId());
        BusStop destinationBusStop = busStopRepository.findById(entityDto.getDestinationBusStopId());
        routing.setName(entityDto.getName());
        routing.setCompany(company);
        routing.setStatus(entityDto.getStatus());
        routing.setOriginBusStop(originBusStop);
        routing.setDestinationBusStop(destinationBusStop);
        Route routeUpdate = routeRepository.update(routing);
        for(RouteUpdateDTO.BusStopDTO routeIterable : entityDto.getBusStops()){
            RouteBusStop routeBusStop = routeBusStopRepository.findById(routeIterable.getId());
            BusStop firstBusStop = busStopRepository.findById(routeIterable.getFirstBusStopId());
            BusStop secondBusStop = busStopRepository.findById(routeIterable.getSecondBusStopId());
            routeBusStop.setOrder(routeIterable.getOrder());
            routeBusStop.setRoute(routeUpdate);
            routeBusStop.setFirstBusStop(firstBusStop);
            routeBusStop.setSecondBusStop(secondBusStop);
            routeBusStopRepository.update(routeBusStop);
        }
        return routeUpdate;
    }

    @Override
    public void deleteById(Integer aLong) {
        routeRepository.deleteById(aLong);
    }




}
