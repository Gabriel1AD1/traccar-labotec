package com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper;

import com.labotec.traccar.domain.database.models.GeofenceCircular;
import com.labotec.traccar.domain.database.models.GeofencePoligonal;
import com.labotec.traccar.domain.database.models.Point;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.GeofenceCircularEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.GeofencePoligonalEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.PointEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GeofenceMapper {

    // Mapeo para geocercas poligonales
    GeofencePoligonal toModel(GeofencePoligonalEntity entity);
    GeofencePoligonalEntity toEntity(GeofencePoligonal model);

    List<GeofencePoligonal> toModelList(List<GeofencePoligonalEntity> entities);
    List<GeofencePoligonalEntity> toEntityList(List<GeofencePoligonal> models);

    // Mapeo para puntos del polígono
    Point toModel(PointEntity entity);
    PointEntity toEntity(Point model);

    List<Point> toPointModelList(List<PointEntity> entities);
    List<PointEntity> toPointEntityList(List<Point> models);

    // Mapeo para geocercas circulares
    GeofenceCircular toModel(GeofenceCircularEntity entity);
    GeofenceCircularEntity toEntity(GeofenceCircular model);

    List<GeofenceCircular> toModelCircularList(List<GeofenceCircularEntity> entities);
    List<GeofenceCircularEntity> toEntityCircularList(List<GeofenceCircular> models);
}