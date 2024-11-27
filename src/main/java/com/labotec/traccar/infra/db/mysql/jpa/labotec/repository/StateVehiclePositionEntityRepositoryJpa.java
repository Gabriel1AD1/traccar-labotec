package com.labotec.traccar.infra.db.mysql.jpa.labotec.repository;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.StateVehiclePositionEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StateVehiclePositionEntityRepositoryJpa extends JpaRepository<StateVehiclePositionEntity, Long> {
    // Usando SQL nativo para obtener el estado del vehículo por traccarDeviceId
    @Query(value = "SELECT * FROM tbl_estado_vehiculo_posicion s WHERE s.vehicle_id = (SELECT v.id_device_traccar FROM tbl_unidades v WHERE v.traccarDeviceId = :traccarDeviceId)", nativeQuery = true)
    Optional<StateVehiclePositionEntity> findByTraccarDeviceId(Long traccarDeviceId);

}
