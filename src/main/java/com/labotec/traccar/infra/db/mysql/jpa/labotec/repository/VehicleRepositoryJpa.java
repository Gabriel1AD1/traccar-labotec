package com.labotec.traccar.infra.db.mysql.jpa.labotec.repository;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepositoryJpa extends JpaRepository<VehicleEntity, Long> {
    // Consultas personalizadas si es necesario
    @Query("SELECT v.traccarDeviceId FROM VehicleEntity v")
    List<Integer> findTraccarDeviceIdBy();

    VehicleEntity findByTraccarDeviceId(Integer traccarDeviceId);
    @Query("SELECT v FROM VehicleEntity v WHERE v.traccarDeviceId = :traccarDeviceId AND v.userId.userId = :userId")
    Optional<VehicleEntity> findByTraccarDeviceIdAndUserIdUserId(@Param("traccarDeviceId") Long traccarDeviceId, @Param("userId") Long userId);

}
