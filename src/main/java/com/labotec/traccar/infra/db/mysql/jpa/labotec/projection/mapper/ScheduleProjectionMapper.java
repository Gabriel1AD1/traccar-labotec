package com.labotec.traccar.infra.db.mysql.jpa.labotec.projection.mapper;

import com.labotec.traccar.domain.query.*;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.projection.*;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ScheduleProjectionMapper {

    public ScheduleProcessPosition toScheduleProcessPosition(ScheduleProjection scheduleProjection) {
        if (scheduleProjection == null) {
            return null;
        }

        ScheduleProcessPosition scheduleProcessPosition = new ScheduleProcessPosition();
        scheduleProcessPosition.setId(scheduleProjection.getId());
        scheduleProcessPosition.setTypeGeofence(scheduleProjection.getGeofenceType());
        scheduleProcessPosition.setGeofenceId(scheduleProjection.getGeofenceId());
        scheduleProcessPosition.setRadiusValidatePolyline(scheduleProjection.getRadiusValidateRoutePolyline());
        scheduleProcessPosition.setValidateRouteExplicit(scheduleProjection.getValidateRouteExplicit());
        scheduleProcessPosition.setState(scheduleProjection.getStatus());

        // Mapear la ruta si está presente
        if (scheduleProjection.getRoute() != null) {
            scheduleProcessPosition.setRoute(mapRouteProjection(scheduleProjection.getRoute()));
        }

        return scheduleProcessPosition;
    }

    private ScheduleRouteProjection mapRouteProjection(RouteProjection routeProjection) {
        if (routeProjection == null) {
            return null;
        }

        ScheduleRouteProjection scheduleRouteProjection = new ScheduleRouteProjection();
        scheduleRouteProjection.setId(routeProjection.getId());
        scheduleRouteProjection.setName(routeProjection.getName());

        // Mapear la lista de paradas de la ruta
        if (routeProjection.getBusStopsList() != null) {
            scheduleRouteProjection.setBusStopsList(routeProjection.getBusStopsList().stream()
                    .map(this::mapRouteBusStopProjection)
                    .collect(Collectors.toList()));
        }

        return scheduleRouteProjection;
    }

    private ScheduleRouteBusStopProjection mapRouteBusStopProjection(RouteBusStopProjection routeBusStopProjection) {
        if (routeBusStopProjection == null) {
            return null;
        }

        ScheduleRouteBusStopProjection scheduleRouteBusStopProjection = new ScheduleRouteBusStopProjection();
        scheduleRouteBusStopProjection.setId(routeBusStopProjection.getId());
        scheduleRouteBusStopProjection.setOrder(routeBusStopProjection.getOrder());
        scheduleRouteBusStopProjection.setMinWaitTime(routeBusStopProjection.getMinWaitTime());
        scheduleRouteBusStopProjection.setMaxWaitTime(routeBusStopProjection.getMaxWaitTime());
        scheduleRouteBusStopProjection.setEstimatedTravelTime(routeBusStopProjection.getEstimatedTravelTime());

        // Mapear el primer y segundo paradero
        if (routeBusStopProjection.getFirstBusStop() != null) {
            scheduleRouteBusStopProjection.setFirstBusStop(mapBusStopProjection(routeBusStopProjection.getFirstBusStop()));
        }

        if (routeBusStopProjection.getSecondBusStop() != null) {
            scheduleRouteBusStopProjection.setSecondBusStop(mapBusStopProjection(routeBusStopProjection.getSecondBusStop()));
        }

        // Mapear las polilíneas asociadas
        if (routeBusStopProjection.getOverviewPolylines() != null) {
            scheduleRouteBusStopProjection.setOverviewPolylines(routeBusStopProjection.getOverviewPolylines().stream()
                    .map(this::mapOverviewPolylineProjection)
                    .collect(Collectors.toList()));
        }

        return scheduleRouteBusStopProjection;
    }

    private ScheduleBusStopProjection mapBusStopProjection(BusStopProjection busStopProjection) {
        if (busStopProjection == null) {
            return null;
        }

        ScheduleBusStopProjection scheduleBusStopProjection = new ScheduleBusStopProjection();
        scheduleBusStopProjection.setId(busStopProjection.getId());
        scheduleBusStopProjection.setName(busStopProjection.getName());
        scheduleBusStopProjection.setDescription(busStopProjection.getDescription());
        scheduleBusStopProjection.setLatitude(busStopProjection.getLatitude());
        scheduleBusStopProjection.setLongitude(busStopProjection.getLongitude());
        scheduleBusStopProjection.setStatus(busStopProjection.getStatus());

        return scheduleBusStopProjection;
    }

    private ScheduleOverviewPolylineProjection mapOverviewPolylineProjection(OverviewPolylineProjection overviewPolylineProjection) {
        if (overviewPolylineProjection == null) {
            return null;
        }

        ScheduleOverviewPolylineProjection scheduleOverviewPolylineProjection = new ScheduleOverviewPolylineProjection();
        scheduleOverviewPolylineProjection.setId(overviewPolylineProjection.getId());
        scheduleOverviewPolylineProjection.setPolyline(overviewPolylineProjection.getPolyline());
        scheduleOverviewPolylineProjection.setIsPrimary(overviewPolylineProjection.getIsPrimary());

        return scheduleOverviewPolylineProjection;
    }
}
