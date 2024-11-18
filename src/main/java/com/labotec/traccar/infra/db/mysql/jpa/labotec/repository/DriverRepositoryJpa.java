package com.labotec.traccar.infra.db.mysql.jpa.labotec.repository;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.DriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepositoryJpa extends JpaRepository<DriverEntity, Integer> {

    // Buscar un DriverEntity por su id y userId
    @Query("SELECT d FROM DriverEntity d WHERE d.id = :driverId AND d.userId.userId = :userId")
    Optional<DriverEntity> findByIdAndUserId(@Param("driverId") Long driverId, @Param("userId") Long userId);

    // Buscar todos los DriverEntity asociados a un userId
    @Query("SELECT d FROM DriverEntity d WHERE d.userId.userId = :userId")
    List<DriverEntity> findAllByUserId(@Param("userId") Long userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM DriverEntity d WHERE d.id = :driverId AND d.userId.userId = :userId")
    void deleteByIdAndUserId(@Param("driverId") Long driverId, @Param("userId") Long userId);
}
