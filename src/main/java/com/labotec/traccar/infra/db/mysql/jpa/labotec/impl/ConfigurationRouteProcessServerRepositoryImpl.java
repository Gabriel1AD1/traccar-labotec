package com.labotec.traccar.infra.db.mysql.jpa.labotec.impl;

import com.labotec.traccar.app.ports.input.repository.ConfigurationRouteProcessServerRepository;
import com.labotec.traccar.domain.database.models.ConfigurationRouteProcessServer;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.ConfigurationRouteProcessServerEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.ConfigurationRouteProcessServerEntityRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ConfigurationRouteProcessServerRepositoryImpl implements ConfigurationRouteProcessServerRepository {

    private final ConfigurationRouteProcessServerEntityRepositoryJpa configurationRouteProcessServerEntityRepositoryJpa;
    @Override
    public ConfigurationRouteProcessServer getConfiguration() {
        ConfigurationRouteProcessServerEntity config = configurationRouteProcessServerEntityRepositoryJpa.findPrimaryConfiguration().orElse(ConfigurationRouteProcessServerEntity.builder().radiusValidateBusStop(15.0).build());
        return ConfigurationRouteProcessServer.builder().radiusValidateBusStop(config.getRadiusValidateBusStop()).build();
    }
}
