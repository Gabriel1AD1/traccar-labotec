package com.labotec.traccar.app.usecase.ports.out;

import com.labotec.traccar.app.utils.common.repository.GenericCrudService;
import com.labotec.traccar.domain.database.models.Company;
import com.labotec.traccar.domain.web.dto.CompanyDTO;

public interface CompanyService extends GenericCrudService<Company , CompanyDTO , Integer> {
}
