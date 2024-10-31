package com.labotec.traccar.app.usecase.ports.out;

import com.labotec.traccar.app.utils.common.repository.GenericCrudService;
import com.labotec.traccar.domain.database.models.Company;
import com.labotec.traccar.domain.web.dto.entel.create.CompanyDTO;
import com.labotec.traccar.domain.web.dto.entel.update.CompanyUpdateDTO;

public interface CompanyService extends GenericCrudService<Company , CompanyDTO, CompanyUpdateDTO> {
}
