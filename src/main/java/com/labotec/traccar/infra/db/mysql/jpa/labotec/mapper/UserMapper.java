package com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper;

import com.labotec.traccar.domain.database.models.User;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CompanyMapper.class})
public interface UserMapper {
    User  toDomain(UserEntity userEntity);
    UserEntity toEntity(User user);
}
