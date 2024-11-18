package com.labotec.traccar.infra.db.mysql.jpa.traccar.repository;

import com.labotec.traccar.infra.db.mysql.jpa.traccar.entity.TcCompany;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TcCompanyRepository extends JpaRepository<TcCompany, Integer> {
}