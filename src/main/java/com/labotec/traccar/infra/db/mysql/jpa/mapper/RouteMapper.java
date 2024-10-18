package com.labotec.traccar.infra.db.mysql.jpa.mapper;

import com.labotec.traccar.domain.database.models.Route;
import com.labotec.traccar.infra.db.mysql.jpa.entity.RouteEntity;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {BusStopMapper.class, CompanyMapper.class})
public interface RouteMapper {

    // De entidad a modelo
    Route toModel(RouteEntity entity);

    // De modelo a entidad
    RouteEntity toEntity(Route model);

    // Mapear listas
    List<Route> toModelList(List<RouteEntity> entities);
    List<RouteEntity> toEntityList(List<Route> models);

    // Mapear conjuntos
    Set<Route> toModelSet(Set<RouteEntity> entities);
    Set<RouteEntity> toEntitySet(Set<Route> models);

    // Mapear mapas
    Map<String, Route> toModelMap(Map<String, RouteEntity> entityMap);
    Map<String, RouteEntity> toEntityMap(Map<String, Route> modelMap);
}