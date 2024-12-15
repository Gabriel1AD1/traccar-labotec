package com.labotec.traccar.app.ports.out.services;

import com.labotec.traccar.domain.database.models.Company;
import com.labotec.traccar.domain.web.labotec.request.create.CompanyDTO;

public interface CompanyService {
    Company create(CompanyDTO companyDTO);

    void deleteCompanyById(Long companyId);
}
