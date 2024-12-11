package com.labotec.traccar.infra.db.mysql.jpa.labotec.repository;

import com.labotec.traccar.domain.web.dto.labotec.response.ResponseVehicle;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.UserEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.VehicleEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.projection.ResponseVehicleProjection;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Repository
public interface VehicleRepositoryJpa extends JpaRepository<VehicleEntity, Long> {
    // Consultas personalizadas si es necesario
    @Query("SELECT v.traccarDeviceId FROM VehicleEntity v")
    List<Integer> findTraccarDeviceIdBy();

    @Query("SELECT v FROM VehicleEntity v WHERE v.traccarDeviceId = :traccarDeviceId AND v.userId.userId = :userId")
    Optional<VehicleEntity> findByTraccarDeviceIdAndUserIdUserId(@Param("traccarDeviceId") Long traccarDeviceId, @Param("userId") Long userId);

    Optional<VehicleEntity> findByLicensePlate(String licencePlate);

    List<VehicleEntity> findAllByUserId_UserId(Long userId);

    @Query("""
    SELECT
        v.traccarDeviceId AS traccarDeviceId,
        v.licensePlate AS licensePlate,
        vt.name AS typeVehicleName,
        v.status AS status,
        v.registerNumber AS registerNumber,
        v.brand AS brand,
        v.model AS model
    FROM VehicleEntity v
    JOIN v.typeVehicle vt
    JOIN v.userId u
    WHERE u.userId = :userId
    """)
    List<ResponseVehicleProjection> findVehiclesByUserId(@Param("userId") Long userId);

}
