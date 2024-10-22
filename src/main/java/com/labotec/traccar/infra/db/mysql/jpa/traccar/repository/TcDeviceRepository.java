package com.labotec.traccar.infra.db.mysql.jpa.traccar.repository;

import com.labotec.traccar.infra.db.mysql.jpa.traccar.entity.TcDevice;
import com.labotec.traccar.infra.db.mysql.jpa.traccar.projections.TcDeviceBasicProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TcDeviceRepository extends JpaRepository<TcDevice, Integer> {
    List<TcDeviceBasicProjection> findByIdNotIn(List<Integer> ids);
}
