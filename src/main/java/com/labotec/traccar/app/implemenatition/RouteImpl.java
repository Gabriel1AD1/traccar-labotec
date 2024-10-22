package com.labotec.traccar.app.implemenatition;

import com.labotec.traccar.app.mapper.RouteModelMapper;
import com.labotec.traccar.app.usecase.ports.input.BusStopRepository;
import com.labotec.traccar.app.usecase.ports.input.CompanyRepository;
import com.labotec.traccar.app.usecase.ports.input.RouteBusStopRepository;
import com.labotec.traccar.app.usecase.ports.input.RouteRepository;
import com.labotec.traccar.app.usecase.ports.out.RouteService;
import com.labotec.traccar.app.utils.common.repository.GenericCrudService;
import com.labotec.traccar.domain.database.models.BusStop;
import com.labotec.traccar.domain.database.models.Company;
import com.labotec.traccar.domain.database.models.Route;
import com.labotec.traccar.domain.database.models.RouteBusStop;
import com.labotec.traccar.domain.web.dto.RouteDTO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RouteImpl implements RouteService
{
    private final RouteRepository routeRepository;
    private final CompanyRepository companyRepository;
    private final BusStopRepository busStopRepository;
    private final RouteBusStopRepository routeBusStopRepository;
    private final RouteModelMapper routeModelMapper;
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
        for(RouteDTO.BusStopOrderDTO routeIterable : entityDto.getBusStops()){
            RouteBusStop routeBusStopCreate = new RouteBusStop();
            BusStop routeBusStop = busStopRepository.findById(routeIterable.getBusStopId());
            routeBusStopCreate.setBusStop(routeBusStop);
            routeBusStopCreate.setOrder(routeBusStopCreate.getOrder());
            routeBusStopCreate.setRoute(routeSave);
            routeBusStopRepository.create(routeBusStopCreate);
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
    public Route update(RouteDTO entityDto, Integer aLong) {
        Route routing = routeRepository.findById(aLong);
        Route routeMap = routeModelMapper.INSTANCE.toRoute(entityDto);
        routeMap.setId(routing.getId());
        return routeRepository.update(routeMap);
    }

    @Override
    public void deleteById(Integer aLong) {
        routeRepository.deleteById(aLong);
    }
}
