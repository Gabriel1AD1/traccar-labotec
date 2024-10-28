package com.labotec.traccar.infra.db.mysql.jpa.labotec.repository;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.OverviewPolylineEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.RouteBusStopEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.RouteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OverviewPolylineRepositoryJpa extends JpaRepository<OverviewPolylineEntity , Integer> {

    @Query("SELECT op FROM OverviewPolylineEntity op WHERE op.routeBusStop = :routeBusStop AND op.isPrimary = true")
    Optional<OverviewPolylineEntity> findByRouteBusStopAndIsPrimary(@Param("routeBusStop") RouteBusStopEntity routeBusStop);

}
