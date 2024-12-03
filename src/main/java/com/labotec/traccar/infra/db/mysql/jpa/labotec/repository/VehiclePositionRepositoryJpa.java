package com.labotec.traccar.infra.db.mysql.jpa.labotec.repository;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.VehiclePositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

public interface VehiclePositionRepositoryJpa extends JpaRepository<VehiclePositionEntity, Long> {
    VehiclePositionEntity findByScheduleId(Long routeId);
    @Modifying
    @Transactional
    @Query("UPDATE VehiclePositionEntity vp " +
            "SET vp.currentTimeStopped = :currentTime " +
            "WHERE vp.scheduleId = :scheduleId " +
            "AND (vp.latitude != :latitude OR vp.longitude != :longitude)")
    int updateStoppedTimeIfLocationChanged(
            @Param("currentTime") Instant currentTime,
            @Param("scheduleId") Long scheduleId,
            @Param("latitude") Double latitude,
            @Param("longitude") Double longitude
    );
    @Modifying
    @Transactional
    @Query("UPDATE VehiclePositionEntity vp " +
            "SET vp.currentBusStop = :currentBusStopId, " +
            "    vp.nexBusStopId = :nextBusStopId, " +
            "    vp.latitude = :latitude, " +
            "    vp.longitude = :longitude, " +
            "    vp.minWaitTimeForBusStop = :minWaitTime, " +
            "    vp.maxWaitTimeForBusStop = :maxWaitTime, " +
            "    vp.currentTimeStoppedForBusStop = :currentTimeStoppedForBusStop, " +
            "    vp.currentSegment = :currentSegmentOrder, " +
            "    vp.whereaboutsStatus = :whereaboutsStatus, " +
            "    vp.resetRoute = :resetRoute, " +
            "    vp.nextMaxBusStopTimeBusStop = :nextMaxBusStopTimeBusStop, " +
            "    vp.nextMinBusStopTimeBusStop = :nextMinBusStopTimeBusStop " +
            "WHERE vp.id = :id " + // Usar el campo 'id' en lugar de 'deviceId'
            "AND (:currentSegmentOrder IS NULL OR vp.currentSegment != :currentSegmentOrder)")
    int updateInformationInitialVehicleByIdIfSegmentChanged(
            @Param("currentBusStopId") Long currentBusStopId,
            @Param("nextBusStopId") Long nextBusStopId,
            @Param("latitude") Double latitude,
            @Param("longitude") Double longitude,
            @Param("minWaitTime") Integer minWaitTime,
            @Param("maxWaitTime") Integer maxWaitTime,
            @Param("currentTimeStoppedForBusStop") Instant currentTimeStoppedForBusStop,
            @Param("currentSegmentOrder") Long currentSegmentOrder,
            @Param("whereaboutsStatus") Boolean whereaboutsStatus,
            @Param("resetRoute") Boolean resetRoute,
            @Param("nextMaxBusStopTimeBusStop") Instant nextMaxBusStopTimeBusStop,
            @Param("nextMinBusStopTimeBusStop") Instant nextMinBusStopTimeBusStop,
            @Param("id") Long id
    );
    @Transactional
    @Modifying
    @Query("UPDATE VehiclePositionEntity v SET v.completeRoute = :completeRoute WHERE v.id = :id")
    int updateCompleteRouteById(@Param("id") Long id, @Param("completeRoute") boolean completeRoute);

}