package com.labotec.traccar.infra.db.mysql.jpa.labotec.repository;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepositoryJpa extends JpaRepository<VehicleEntity, Integer> {
    // Consultas personalizadas si es necesario
    @Query("SELECT v.traccarDeviceId FROM VehicleEntity v")

    List<Integer> findTraccarDeviceIdBy();

}
