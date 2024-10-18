package com.labotec.traccar.infra.db.mysql.jpa.impl;

import com.labotec.traccar.app.usecase.ports.input.RouteRepository;
import com.labotec.traccar.domain.database.models.Route;
import com.labotec.traccar.domain.web.dto.RouteDTO;
import com.labotec.traccar.infra.db.mysql.jpa.entity.BusStopEntity;
import com.labotec.traccar.infra.db.mysql.jpa.entity.CompanyEntity;
import com.labotec.traccar.infra.db.mysql.jpa.entity.RouteEntity;
import com.labotec.traccar.infra.db.mysql.jpa.mapper.RouteMapper;
import com.labotec.traccar.infra.db.mysql.jpa.repository.BusStopRepositoryJpa;
import com.labotec.traccar.infra.db.mysql.jpa.repository.CompanyRepositoryJpa;
import com.labotec.traccar.infra.db.mysql.jpa.repository.RouteRepositoryJpa;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.labotec.traccar.infra.db.mysql.jpa.message.ComapanyMessage.COMPANY_NOT_FOUND_BY_ID;
import static com.labotec.traccar.infra.db.mysql.jpa.message.RouteMessage.ROUTE_NOT_FOUND_BY_ID;
import static com.labotec.traccar.infra.db.mysql.jpa.message.BusStopMessage.BUS_STOP_NOT_FOUND_BY_ID;

@AllArgsConstructor
@Repository
public class RouteRepositoryImpl implements RouteRepository {

    private final RouteMapper routeMapper;
    private final RouteRepositoryJpa routeRepositoryJpa;
    private final CompanyRepositoryJpa companyRepositoryJpa;
    private final BusStopRepositoryJpa busStopRepositoryJpa;

    @Override
    public Route create(RouteDTO entity) {
        // Buscar la compañía asociada
        CompanyEntity company = companyRepositoryJpa.findById(entity.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException(COMPANY_NOT_FOUND_BY_ID + entity.getCompanyId()));

        // Buscar el paradero de origen y destino, si existen
        BusStopEntity originBusStop = entity.getOriginBusStopId() != null
                ? busStopRepositoryJpa.findById(entity.getOriginBusStopId())
                .orElseThrow(() -> new EntityNotFoundException(BUS_STOP_NOT_FOUND_BY_ID + entity.getOriginBusStopId()))
                : null;

        BusStopEntity destinationBusStop = entity.getDestinationBusStopId() != null
                ? busStopRepositoryJpa.findById(entity.getDestinationBusStopId())
                .orElseThrow(() -> new EntityNotFoundException(BUS_STOP_NOT_FOUND_BY_ID + entity.getDestinationBusStopId()))
                : null;

        // Crear una entidad RouteEntity manualmente a partir del DTO
        RouteEntity routeEntity = new RouteEntity();
        routeEntity.setName(entity.getName());
        routeEntity.setStatus(entity.getStatus());
        routeEntity.setCompany(company);
        routeEntity.setOriginBusStop(originBusStop);
        routeEntity.setDestinationBusStop(destinationBusStop);

        // Guardar la entidad en la base de datos
        RouteEntity savedRoute = routeRepositoryJpa.save(routeEntity);

        // Devolver el modelo de dominio Route usando el RouteMapper
        return routeMapper.toModel(savedRoute);
    }

    @Override
    public Route findById(Integer id) {
        RouteEntity routeEntity = routeRepositoryJpa.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ROUTE_NOT_FOUND_BY_ID + id));
        return routeMapper.toModel(routeEntity);
    }

    @Override
    public Optional<Route> findByIdOptional(Integer id) {
        return routeRepositoryJpa.findById(id)
                .map(routeMapper::toModel); // Usar el mapper para convertir de entidad a modelo
    }

    @Override
    public List<Route> findAll() {
        List<RouteEntity> routeEntities = routeRepositoryJpa.findAll();
        return routeMapper.toModelList(routeEntities); // Usar el mapper para convertir la lista de entidades
    }

    @Override
    public Route update(RouteDTO entity, Integer id) {
        // Buscar la entidad RouteEntity por su ID
        RouteEntity routeEntity = routeRepositoryJpa.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ROUTE_NOT_FOUND_BY_ID + id));

        // Buscar la compañía, paradero de origen y paradero de destino asociados
        CompanyEntity company = companyRepositoryJpa.findById(entity.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException(COMPANY_NOT_FOUND_BY_ID + entity.getCompanyId()));

        BusStopEntity originBusStop = entity.getOriginBusStopId() != null
                ? busStopRepositoryJpa.findById(entity.getOriginBusStopId())
                .orElseThrow(() -> new EntityNotFoundException(BUS_STOP_NOT_FOUND_BY_ID + entity.getOriginBusStopId()))
                : null;

        BusStopEntity destinationBusStop = entity.getDestinationBusStopId() != null
                ? busStopRepositoryJpa.findById(entity.getDestinationBusStopId())
                .orElseThrow(() -> new EntityNotFoundException(BUS_STOP_NOT_FOUND_BY_ID + entity.getDestinationBusStopId()))
                : null;

        // Actualizar manualmente los campos con los valores del DTO
        routeEntity.setName(entity.getName());
        routeEntity.setStatus(entity.getStatus());
        routeEntity.setCompany(company);
        routeEntity.setOriginBusStop(originBusStop);
        routeEntity.setDestinationBusStop(destinationBusStop);

        // Guardar la entidad actualizada en la base de datos
        RouteEntity updatedRoute = routeRepositoryJpa.save(routeEntity);

        // Devolver el modelo de dominio actualizado usando el mapper
        return routeMapper.toModel(updatedRoute);
    }

    @Override
    public void deleteById(Integer id) {
        routeRepositoryJpa.deleteById(id);
    }
}
