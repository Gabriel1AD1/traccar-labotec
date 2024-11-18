package com.labotec.traccar.app.usecase.ports.input.repository;

import com.labotec.traccar.app.utils.common.repository.GenericRepository;
import com.labotec.traccar.domain.database.models.Company;

import java.util.Optional;

public interface CompanyRepository  {
    Company create(Company entity);
    Company findById(Long resourceId);
    Optional<Company> findByIdOptional(Long resourceId);
    Iterable<Company> findAll();
    Company update(Company entity);
    void deleteById(Long resourceId);

}
