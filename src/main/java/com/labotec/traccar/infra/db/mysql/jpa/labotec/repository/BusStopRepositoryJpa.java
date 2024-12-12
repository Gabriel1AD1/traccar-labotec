package com.labotec.traccar.infra.db.mysql.jpa.labotec.repository;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.BusStopEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.projection.BusStopProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface BusStopRepositoryJpa extends JpaRepository<BusStopEntity, Long> {
    @Query("SELECT b FROM BusStopEntity b WHERE b.id = :busStopId AND b.userId.userId = :userId")
    Optional<BusStopEntity> findByIdAndUserId(@Param("busStopId") Long busStopId, @Param("userId") Long userId);
    @Query("SELECT b FROM BusStopEntity b WHERE b.userId.userId = :userId")
    List<BusStopEntity> findAllByUserId(@Param("userId") Long userId);
    @Transactional
    @Modifying
    @Query("DELETE FROM BusStopEntity b WHERE b.id = :resourceId AND b.userId.userId = :userId")
    void deleteByUserIdAndCompanyId(@Param("resourceId") Long resourceId, @Param("userId") Long userId);

    @Query("select (count(b) > 0) from BusStopEntity b where b.id = ?1 and b.userId.userId = ?2")
    boolean existsByIdAndUserId(Long resourceId, Long userId);


    @Query("SELECT b.id AS id, b.name AS name, b.description AS description, b.latitude AS latitude, b.longitude AS longitude, b.status AS status " +
            "FROM BusStopEntity b " +
            "WHERE b.id = :busStopId")
    Optional<BusStopProjection> findByResourceId(Long busStopId);}

