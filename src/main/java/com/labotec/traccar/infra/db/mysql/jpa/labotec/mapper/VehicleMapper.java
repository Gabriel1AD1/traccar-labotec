package com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper;

import com.labotec.traccar.domain.database.models.Vehicle;
import com.labotec.traccar.domain.database.models.VehicleType;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.VehicleEntity;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {CompanyMapper.class ,VehicleTypeMapper.class})
public interface VehicleMapper {

    // De entidad a modelo
    Vehicle toModel(VehicleEntity entity);

    // De modelo a entidad
    VehicleEntity toEntity(Vehicle model);


    // Mapear listas
    List<Vehicle> toModelList(List<VehicleEntity> entities);
    List<VehicleEntity> toEntityList(List<Vehicle> models);

    // Mapear conjuntos
    Set<Vehicle> toModelSet(Set<VehicleEntity> entities);
    Set<VehicleEntity> toEntitySet(Set<Vehicle> models);

    // Mapear mapas
    Map<String, Vehicle> toModelMap(Map<String, VehicleEntity> entityMap);
    Map<String, VehicleEntity> toEntityMap(Map<String, Vehicle> modelMap);
}