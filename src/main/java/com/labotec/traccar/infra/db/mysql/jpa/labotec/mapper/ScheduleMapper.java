package com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper;

import com.labotec.traccar.domain.database.models.Schedule;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.ScheduleEntity;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {
        VehicleMapper.class,
        DriverMapper.class,
        RouteMapper.class,
        CompanyMapper.class,
        DriverScheduleMapper.class

})
public interface ScheduleMapper {

    // De entidad a modelo
    Schedule toModel(ScheduleEntity entity);

    // De modelo a entidad
    ScheduleEntity toEntity(Schedule model);

    // Mapear listas
    List<Schedule> toModelList(List<ScheduleEntity> entities);
    List<ScheduleEntity> toEntityList(List<Schedule> models);

    // Mapear conjuntos
    Set<Schedule> toModelSet(Set<ScheduleEntity> entities);
    Set<ScheduleEntity> toEntitySet(Set<Schedule> models);

    // Mapear mapas
    Map<String, Schedule> toModelMap(Map<String, ScheduleEntity> entityMap);
    Map<String, ScheduleEntity> toEntityMap(Map<String, Schedule> modelMap);
}