package com.labotec.traccar.infra.web.controller.rest.labotec;

import com.labotec.traccar.app.usecase.ports.out.CompanyService;
import com.labotec.traccar.domain.database.models.Company;
import com.labotec.traccar.domain.web.dto.CompanyDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    @GetMapping("/{id}")
    public ResponseEntity<Company> findById(@PathVariable @NotNull Integer id) {
        Optional<Company> result = Optional.ofNullable(companyService.findById(id));
        return result.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para obtener todas las compañías
    @GetMapping("")
    public ResponseEntity<Iterable<Company>> findAll() {
        Iterable<Company> allCompanies = companyService.findAll();
        return ResponseEntity.ok(allCompanies);
    }

    // Endpoint para actualizar una compañía existente
    @PutMapping("/{id}")
    public ResponseEntity<Company> update(@RequestBody @Valid CompanyDTO companyDTO, @PathVariable @NotNull Integer id) {
        Company updatedCompany = companyService.update(companyDTO, id);
        return ResponseEntity.ok(updatedCompany);
    }

    // Endpoint para eliminar una compañía por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable @NotNull Integer id) {
        companyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
