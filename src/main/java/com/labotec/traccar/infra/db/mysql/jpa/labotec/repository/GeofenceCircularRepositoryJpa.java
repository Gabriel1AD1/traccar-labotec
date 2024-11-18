package com.labotec.traccar.infra.db.mysql.jpa.labotec.repository;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.CircularGeofenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface GeofenceCircularRepositoryJpa extends JpaRepository<CircularGeofenceEntity, Long> {
    // Eliminar un CircularGeofenceEntity por su id y userId
    @Transactional
    @Modifying
    @Query("DELETE FROM CircularGeofenceEntity c WHERE c.id = :geofenceId AND c.userId.userId = :userId")
    void deleteByIdAndUserId(@Param("geofenceId") Long geofenceId, @Param("userId") Long userId);

    // Otros métodos como findByIdAndUserId y findAllByUserId
    @Query("SELECT c FROM CircularGeofenceEntity c WHERE c.id = :geofenceId AND c.userId.userId = :userId")
    Optional<CircularGeofenceEntity> findByIdAndUserId(@Param("geofenceId") Long geofenceId, @Param("userId") Long userId);

    @Query("SELECT c FROM CircularGeofenceEntity c WHERE c.userId.userId = :userId")
    List<CircularGeofenceEntity> findAllByUserId(@Param("userId") Long userId);
}