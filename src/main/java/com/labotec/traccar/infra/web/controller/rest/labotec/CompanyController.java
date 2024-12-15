package com.labotec.traccar.infra.web.controller.rest.labotec;

import com.labotec.traccar.app.ports.out.services.CompanyService;
import com.labotec.traccar.domain.database.models.Company;
import com.labotec.traccar.domain.web.labotec.request.create.CompanyDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.labotec.traccar.infra.web.controller.common.API_VERSION_MANAGER.API_VERSION_V1;

@RestController
@RequestMapping(value = API_VERSION_V1 + "company")
@AllArgsConstructor
public class CompanyController {

    // Inyección del servicio específico de Company
    private final CompanyService companyService;

    // Endpoint para crear una nueva compañía
    @PostMapping("")
    public ResponseEntity<Company> create(@RequestBody @Valid CompanyDTO companyDTO) {
        Company createdCompany = companyService.create(companyDTO);
        return ResponseEntity.ok(createdCompany);
    }

    // Endpoint para obtener una compañía por su ID
    @PutMapping("/{id}")
    public ResponseEntity<Company> findById(@PathVariable("id") @NotNull Long resourceId) {
        companyService.deleteCompanyById(resourceId);
        return ResponseEntity.ok().build();
    }


}
