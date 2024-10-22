package com.labotec.traccar.infra.web.controller.common;

import com.labotec.traccar.app.utils.common.repository.GenericCrudService;
import com.labotec.traccar.domain.database.models.Company;
import com.labotec.traccar.domain.web.dto.CompanyDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.Optional;

public interface CrudController<MODELO, DTO, ID> {

    @PostMapping("")
    default ResponseEntity<MODELO> create(@RequestBody @Valid DTO dto, GenericCrudService<MODELO, DTO, ID> service) {
        MODELO created = service.create(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    default ResponseEntity<MODELO> findById(@PathVariable @NotNull ID id, GenericCrudService<MODELO, DTO, ID> service) {
        Optional<MODELO> result = Optional.ofNullable(service.findById(id));
        return result.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("")
    default ResponseEntity<Iterable<MODELO>> findAll(GenericCrudService<MODELO, DTO, ID> service) {
        Iterable<MODELO> allItems = service.findAll();
        return ResponseEntity.ok(allItems);
    }

    @PutMapping("/{id}")
    default ResponseEntity<MODELO> update(@RequestBody @Valid DTO dto, @PathVariable @NotNull ID id, GenericCrudService<MODELO, DTO, ID> service) {
        MODELO updated = service.update(dto, id);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    default ResponseEntity<Void> deleteById(@PathVariable @NotNull ID id, GenericCrudService<MODELO, DTO, ID> service) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
