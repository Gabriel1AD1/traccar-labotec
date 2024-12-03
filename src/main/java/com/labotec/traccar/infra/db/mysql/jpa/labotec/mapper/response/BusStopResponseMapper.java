package com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.response;

import com.labotec.traccar.domain.database.models.User;
import com.labotec.traccar.domain.web.dto.labotec.response.BusStopResponse;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.BusStopEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.CompanyMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface BusStopResponseMapper {
    @Mappings({
            @Mapping(source = "userId.userId", target = "userId"), // Mapea userId del objeto userId a userId
            @Mapping(source = "companyId.companyId", target = "companyId") // Mapea companyId del objeto companyId a companyId
    })
    BusStopResponse toModel(BusStopEntity busStopEntity);
}
