package com.labotec.traccar.infra.db.mysql.jpa.labotec.repository;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.VehicleTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface VehicleTypeRepositoryJpa extends JpaRepository<VehicleTypeEntity , Long> {


    @Query("SELECT v FROM VehicleTypeEntity v WHERE v.id = :id AND v.userId.userId = :userId")
    Optional<VehicleTypeEntity> findByIdAndUserId(@Param("id") Long resourceId, @Param("userId") Long userId);}
