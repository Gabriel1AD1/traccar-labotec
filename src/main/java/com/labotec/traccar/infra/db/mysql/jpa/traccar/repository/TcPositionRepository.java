package com.labotec.traccar.infra.db.mysql.jpa.traccar.repository;

import com.labotec.traccar.infra.db.mysql.jpa.traccar.entity.TcPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TcPositionRepository extends JpaRepository<TcPosition,Integer> {

    List<TcPosition> findTop20ByOrderByIdDesc();
}
