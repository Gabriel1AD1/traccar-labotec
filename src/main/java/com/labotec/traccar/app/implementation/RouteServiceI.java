package com.labotec.traccar.app.implementation;

import com.labotec.traccar.app.enums.RouteType;
import com.labotec.traccar.app.mapper.model.RouteModelMapper;
import com.labotec.traccar.app.ports.input.repository.*;
import com.labotec.traccar.app.ports.out.services.RouteService;
import com.labotec.traccar.app.utils.RouteUtils;
import com.labotec.traccar.domain.database.models.*;
import com.labotec.traccar.domain.database.models.read.RouteBusStopInformation;
import com.labotec.traccar.domain.web.dto.labotec.request.create.PolylineCreateDTO;
import com.labotec.traccar.domain.web.dto.labotec.request.create.RouteBusStopCreateDTO;
import com.labotec.traccar.domain.web.dto.labotec.request.create.RouteCreateDTO;
import com.labotec.traccar.domain.web.dto.labotec.request.create.RouteSegmentCreateDTO;
import com.labotec.traccar.domain.web.dto.labotec.request.update.RouteUpdateDTO;
import com.labotec.traccar.domain.web.dto.labotec.response.ResponseRoute;
import com.labotec.traccar.domain.web.dto.labotec.response.ResponseRouteBusStopInformation;
import com.labotec.traccar.infra.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
public class RouteServiceI implements RouteService
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
    private final RouteBusStopSegmentRepository routeBusStopSegmentRepository;

    @Override
    public Route create(RouteCreateDTO routeCreateDTO, Long userId) {
        //1 - Obtenemos el usuario por su userId
        User user = userRepository.findByUserId(userId);
        //2 - Obtenemos la compañía de el usuario
        Company company = user.getCompanyId();

        //5 - Mapeamos el dto a un objeto de dominio
        Route route = routeModelMapper.toRoute(routeCreateDTO);
        //6 - Enriquecemos La ruta
        route.setUserId(user);
        route.setCompanyId(company);

        route.setDistanceMaxInKM(routeCreateDTO.getDistanceMaxInKM());
        route.setDistanceMinInKM(routeCreateDTO.getDistanceMinInKM());
        Integer sumEstimatedNextTravelTime = RouteUtils.sumEstimatedNextTravelTime(routeCreateDTO.getSegmentsBusStop());
        Integer minWaitTimeForBusStop = RouteUtils.sumMinWaitTime(routeCreateDTO.getSegmentsBusStop());
        Integer maxWaitTimeForBusStop = RouteUtils.sumMaxWaitTime(routeCreateDTO.getSegmentsBusStop());
        route.setSumNexArrivalTIme(sumEstimatedNextTravelTime);
        route.setSunMinWaitTimeForBusStop(minWaitTimeForBusStop);
        route.setSumMaxWaitTimeForBusStop(maxWaitTimeForBusStop);
        RouteType routeType = RouteUtils.checkRouteLength(routeCreateDTO.getDistanceMaxInKM(),routeCreateDTO.getDistanceMinInKM());
        route.setRouteType(routeType);
        //7- Ruta creada
        Route routeSave = routeRepository.create(route);


        //8- Creamos la tabla intermedia de rutas y paraderos
        for (RouteSegmentCreateDTO routeBusStopSegment : routeCreateDTO.getSegmentsBusStop()){
            RouteBusStopSegment routeBusStopSegmentSave = new RouteBusStopSegment();
            busStopRepository.validateIdBusStop(routeBusStopSegment.getStartBusStopId(),userId);
            routeBusStopSegmentSave.setBusStop(new BusStop(routeBusStopSegment.getStartBusStopId()));
            routeBusStopSegmentSave.setTypeBusStop(routeBusStopSegment.getTypeBusStop());
            routeBusStopSegmentSave.setRoute(routeSave);
            routeBusStopSegmentSave.setOrder(routeBusStopSegment.getOrder());
            routeBusStopSegmentSave.setMaxWaitTime(routeBusStopSegment.getMaxWaitTime());
            routeBusStopSegmentSave.setMinWaitTime(routeBusStopSegment.getMinWaitTime());
            routeBusStopSegmentSave.setEstimatedTravelTime(routeBusStopSegment.getEstimatedNextTravelTime());
            routeBusStopSegmentRepository.create(routeBusStopSegmentSave);
        }
        for(RouteBusStopCreateDTO routeIterable : routeCreateDTO.getSegments()){
            RouteBusStop routeBusStopCreate = new RouteBusStop();
            //9- Buscamos los paraderos
            busStopRepository.validateIdBusStop(routeIterable.getStartBusStopId(),userId);
            busStopRepository.validateIdBusStop(routeIterable.getEndBusStopId(),userId);
            routeBusStopCreate.setFirstBusStop(new BusStop(routeIterable.getStartBusStopId()));
            routeBusStopCreate.setSecondBusStop(new BusStop(routeIterable.getEndBusStopId()));
            routeBusStopCreate.setOrder(routeIterable.getOrder());
            routeBusStopCreate.setRoute(routeSave);
            RouteBusStop routeBusStopSaved = routeBusStopRepository.create(routeBusStopCreate);
                for(PolylineCreateDTO polylineCreateDTO : routeIterable.getPolylines()){
                    OverviewPolyline overviewPolyline = new OverviewPolyline();
                    overviewPolyline.setPolyline(polylineCreateDTO.getPolyline());
                    overviewPolyline.setRouteBusStop(routeBusStopSaved);
                    overviewPolyline.setIsPrimary(polylineCreateDTO.getIsPrimary());
                    overviewPolylineRepository.create(overviewPolyline);

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
            for (RouteUpdateDTO.BusStopCreateDTO  busStopDTO: routeUpdateDTO.getBusStops()) {
                for(PolylineCreateDTO polylineDTO : busStopDTO){
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

    @Override
    public List<ResponseRoute> findAllByUserId(Long userId) {
        return routeRepository.findAllRouteByUserId(userId);
    }

    @Override
    public List<ResponseRouteBusStopInformation> findAllByRouteId(Long routeId, Long userId) {
        checkAccessInformationRoute(routeId,userId);
        List<RouteBusStopInformation> routeBusStopInformation = routeBusStopRepository.findByRouteId(routeId);
        List<ResponseRouteBusStopInformation> response = new ArrayList<>();
        routeBusStopInformation.forEach(s -> response.add(
                ResponseRouteBusStopInformation.builder()
                        .order(s.getOrder())
                        .fistBusStop(busStopRepository.findByResourceId(s.getFirstBusStopId()))
                        .secondBusStop(busStopRepository.findByResourceId(s.getSecondBusStopId()))
                        .overviewPolylines(overviewPolylineRepository.findByRouteBusStopId(s.getId()))
                        .build()
        ));
        return response;
    }

    private void checkAccessInformationRoute(Long routeId, Long userId){
        boolean checkAccessInformation = routeRepository.checkRouteAndUserId(routeId,userId);
        if (! checkAccessInformation){
            throw new EntityNotFoundException("La entitad no ha sido encontrada  " + routeId );
        }
    }

}
