package com.labotec.traccar.infra.db.mysql.jpa.labotec.repository;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.BusStopEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface BusStopRepositoryJpa extends JpaRepository<BusStopEntity, Integer> {
    @Query("SELECT b FROM BusStopEntity b WHERE b.id = :busStopId AND b.userId.userId = :userId")
    Optional<BusStopEntity> findByIdAndUserId(@Param("busStopId") Long busStopId, @Param("userId") Long userId);
    @Query("SELECT b FROM BusStopEntity b WHERE b.userId.userId = :userId")
    List<BusStopEntity> findAllByUserId(@Param("userId") Long userId);
    @Transactional
    @Modifying
    @Query("DELETE FROM BusStopEntity b WHERE b.id = :resourceId AND b.userId.userId = :userId")
    void deleteByUserIdAndCompanyId(@Param("resourceId") Long resourceId, @Param("userId") Long userId);
}

