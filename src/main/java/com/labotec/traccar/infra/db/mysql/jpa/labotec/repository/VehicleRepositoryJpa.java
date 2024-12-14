package com.labotec.traccar.infra.db.mysql.jpa.labotec.repository;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.VehicleEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.projection.ResponseVehicleProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepositoryJpa extends JpaRepository<VehicleEntity, Long> {
    // Consultas personalizadas si es necesario
    @Query("SELECT v.deviceId FROM VehicleEntity v")
    List<Integer> findTraccarDeviceIdBy();

    @Query("SELECT v FROM VehicleEntity v WHERE v.deviceId = :traccarDeviceId AND v.userId.userId = :userId")
    Optional<VehicleEntity> findByTraccarDeviceIdAndUserIdUserId(@Param("traccarDeviceId") Long traccarDeviceId, @Param("userId") Long userId);

    Optional<VehicleEntity> findByLicensePlate(String licencePlate);

    List<VehicleEntity> findAllByUserId_UserId(Long userId);

    @Query("""
    SELECT
        v.deviceId AS traccarDeviceId,
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
    @Transactional
    @Modifying
    @Query("DELETE FROM VehicleEntity v WHERE v.userId.userId = :userId AND v.deviceId = :deviceId")
    int deleteByUserIdAndDeviceId(@Param("userId") Long userId, @Param("deviceId") Long deviceId);

    /**
     * Encuentra la placa del vehículo (licensePlate) por su traccarDeviceId.
     *
     * @param traccarDeviceId ID del dispositivo Traccar.
     * @return La placa del vehículo (licensePlate).
     */
    @Query("SELECT v.licensePlate FROM VehicleEntity v WHERE v.deviceId = :traccarDeviceId")
    String findLicensePlateByTraccarDeviceId(@Param("traccarDeviceId") Long traccarDeviceId);
    // Consulta para verificar si existe un vehículo asociado a un usuario
    // Consulta que verifica si existe un vehículo asociado al userId y traccarDeviceId
    @Query("SELECT CASE WHEN COUNT(v) > 0 THEN true ELSE false END " +
            "FROM VehicleEntity v " +
            "WHERE v.userId.userId = :userId AND v.deviceId = :deviceId")
    Boolean existsByUserIdAndDeviceId(@Param("userId") Long userId, @Param("deviceId") Long deviceId);
}
