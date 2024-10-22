package com.labotec.traccar.app.usecase.ports.input;

import com.labotec.traccar.app.utils.common.repository.GenericRepository;
import com.labotec.traccar.domain.database.models.Company;
import com.labotec.traccar.domain.web.dto.CompanyDTO;

public interface CompanyRepository extends GenericRepository<Company, Integer> {
}
