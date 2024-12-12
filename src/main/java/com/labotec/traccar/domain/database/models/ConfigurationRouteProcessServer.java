package com.labotec.traccar.domain.database.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.ConfigurationRouteProcessServerEntity}
 */
@Value
@Builder
@AllArgsConstructor
public class ConfigurationRouteProcessServer implements Serializable {
    Long id;
    Double radiusValidateBusStop;
    Boolean configurationPrimary;
}