package com.labotec.traccar.infra.db.mysql.jpa.traccar.repository;

import com.labotec.traccar.infra.db.mysql.jpa.traccar.entity.TcUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TcUserRepository extends JpaRepository<TcUser, Integer> {
}