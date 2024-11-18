package com.labotec.traccar.app.implementation;

import com.labotec.traccar.app.mapper.RouteModelMapper;
import com.labotec.traccar.app.usecase.ports.input.repository.*;
import com.labotec.traccar.app.usecase.ports.out.RouteService;
import com.labotec.traccar.domain.database.models.*;
import com.labotec.traccar.domain.enums.STATE;
import com.labotec.traccar.domain.web.dto.entel.create.CustomRouteBusStopDTO;
import com.labotec.traccar.domain.web.dto.entel.create.PolylineDTO;
import com.labotec.traccar.domain.web.dto.entel.create.RouteDTO;
import com.labotec.traccar.domain.web.dto.entel.update.RouteUpdateDTO;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class RouteServiceImpl implements RouteService
{
    private final RouteRepository routeRepository;
    private final CompanyRepository companyRepository;
    private final BusStopRepository busStopRepository;
    private final RouteBusStopRepository routeBusStopRepository;
    private final RouteModelMapper routeModelMapper;
    private final OverviewPolylineRepository overviewPolylineRepository;
    private final VehicleRepository vehicleRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;


    @Override
    public Route create(RouteDTO routeDTO, Long userId) {
        //1 - Obtenemos el usuario por su userId
        User user = userRepository.findByUserId(userId);
        //2 - Obtenemos la compañía de el usuario
        Company company = user.getCompanyId();

        //3 - Obtener el paradero de inicio de la ruta
        BusStop originBusStop  = busStopRepository.findById(routeDTO.getOriginBusStopId(),userId);
        //4 - Obtenemos el ultimo paradero de la ruta
        BusStop destinationBusStop = busStopRepository.findById(routeDTO.getDestinationBusStopId(),userId);
        //5 - Mapeamos el dto a un objeto de dominio
        Route route = routeModelMapper.toRoute(routeDTO);
        //6 - Enriquecemos La ruta
        route.setUserId(user);
        route.setCompanyId(company);
        route.setOriginBusStop(originBusStop);
        route.setDestinationBusStop(destinationBusStop);
        //7- Ruta creada
        Route routeSave = routeRepository.create(route);
        //8- Creamos la tabla intermedia de rutas y paraderos
        for(CustomRouteBusStopDTO routeIterable : routeDTO.getBusStops()){
            RouteBusStop routeBusStopCreate = new RouteBusStop();
            //9- Buscamos los paraderos
            BusStop firstBusStop = busStopRepository.findById(routeIterable.getFirstBusStopId(),userId);
            BusStop secondBusStop = busStopRepository.findById(routeIterable.getSecondBusStopId(),userId);
            routeBusStopCreate.setFirstBusStop(firstBusStop);
            routeBusStopCreate.setSecondBusStop(secondBusStop);
            routeBusStopCreate.setOrder(routeIterable.getOrder());
            routeBusStopCreate.setRoute(routeSave);
            RouteBusStop routeBusStopSaved = routeBusStopRepository.create(routeBusStopCreate);
            for (CustomRouteBusStopDTO  busStopDTO: routeDTO.getBusStops()) {
                for(PolylineDTO polylineDTO : busStopDTO.getPolylines()){
                    OverviewPolyline overviewPolyline = new OverviewPolyline();
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
    public Route findById(Long resourceId, Long userId) {
        return routeRepository.findById(resourceId,userId);
    }

    @Override
    public Iterable<Route> findAll(Long userId) {
        return routeRepository.findAll(userId);
    }

    @Override
    public Route update(RouteUpdateDTO routeUpdateDTO, Long resourceId, Long userId) {
        //1 - Obtenemos el usuario por su userId
        User user = userRepository.findByUserId(userId);
        //2 - Obtenemos la compañía de el usuario
        Company company = user.getCompanyId();

        //3 - Obtener el paradero de inicio de la ruta
        BusStop originBusStop  = busStopRepository.findById(routeUpdateDTO.getOriginBusStopId(),userId);
        //4 - Obtenemos el ultimo paradero de la ruta
        BusStop destinationBusStop = busStopRepository.findById(routeUpdateDTO.getDestinationBusStopId(),userId);
        //5 - Mapeamos el dto a un objeto de dominio
        Route route = new Route();
        route.setName(routeUpdateDTO.getName());
        //6 - Enriquecemos La ruta
        route.setUserId(user);
        route.setCompanyId(company);
        route.setOriginBusStop(originBusStop);
        route.setDestinationBusStop(destinationBusStop);
        //7- Ruta creada
        Route routeSave = routeRepository.create(route);
        //8- Creamos la tabla intermedia de rutas y paraderos
        for(RouteUpdateDTO.BusStopDTO routeIterable : routeUpdateDTO.getBusStops()){
            RouteBusStop routeBusStopCreate = new RouteBusStop();
            //9- Buscamos los paraderos
            BusStop firstBusStop = busStopRepository.findById(routeIterable.getFirstBusStopId(),userId);
            BusStop secondBusStop = busStopRepository.findById(routeIterable.getSecondBusStopId(),userId);
            routeBusStopCreate.setFirstBusStop(firstBusStop);
            routeBusStopCreate.setSecondBusStop(secondBusStop);
            routeBusStopCreate.setOrder(routeIterable.getOrder());
            routeBusStopCreate.setRoute(routeSave);
            RouteBusStop routeBusStopSaved = routeBusStopRepository.create(routeBusStopCreate);/*
            for (RouteUpdateDTO.BusStopDTO  busStopDTO: routeUpdateDTO.getBusStops()) {
                for(PolylineDTO polylineDTO : busStopDTO){
                    OverviewPolyline overviewPolyline = new OverviewPolyline();
                    overviewPolyline.setPolyline(polylineDTO.getPolyline());
                    overviewPolyline.setRouteBusStop(routeBusStopSaved);
                    overviewPolyline.setIsPrimary(polylineDTO.getIsPrimary());
                    overviewPolylineRepository.create(overviewPolyline);

                }

            }*/
        }
        return routeSave;
    }

    @Override
    public void deleteById(Long resourceId, Long userId) {
        routeRepository.deleteById(resourceId,userId);
    }
}
