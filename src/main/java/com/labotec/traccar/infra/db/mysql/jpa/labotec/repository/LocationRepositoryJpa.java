package com.labotec.traccar.infra.db.mysql.jpa.labotec.repository;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepositoryJpa extends JpaRepository<LocationEntity, Integer> {
    // Consulta para eliminar un LocationEntity por id y userId
    @Transactional
    @Modifying
    @Query("DELETE FROM LocationEntity l WHERE l.id = :locationId AND l.userId.userId = :userId")
    void deleteByIdAndUserId(@Param("locationId") Long locationId, @Param("userId") Long userId);

    // Otros métodos de consulta
    @Query("SELECT l FROM LocationEntity l WHERE l.id = :locationId AND l.userId.userId = :userId")
    Optional<LocationEntity> findByIdAndUserId(@Param("locationId") Long locationId, @Param("userId") Long userId);

    @Query("SELECT l FROM LocationEntity l WHERE l.userId.userId = :userId")
    List<LocationEntity> findAllByUserId(@Param("userId") Long userId);
}
