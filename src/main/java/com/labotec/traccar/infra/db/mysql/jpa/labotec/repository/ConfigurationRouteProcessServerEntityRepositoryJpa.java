package com.labotec.traccar.infra.db.mysql.jpa.labotec.repository;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.ConfigurationRouteProcessServerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ConfigurationRouteProcessServerEntityRepositoryJpa extends JpaRepository<ConfigurationRouteProcessServerEntity, Long> {

  @Query("SELECT c FROM configuracion_procesador_rutas c WHERE c.configurationPrimary = true")
  Optional<ConfigurationRouteProcessServerEntity> findPrimaryConfiguration();

}