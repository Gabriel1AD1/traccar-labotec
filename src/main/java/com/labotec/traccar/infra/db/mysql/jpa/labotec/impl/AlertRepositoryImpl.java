package com.labotec.traccar.infra.db.mysql.jpa.labotec.impl;

import com.labotec.traccar.app.ports.input.repository.AlertRepository;
import com.labotec.traccar.domain.database.models.Alert;
import com.labotec.traccar.domain.web.dto.labotec.response.ResponseAlert;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.AlertEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.mapper.AlertMapper;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.projection.ResponseAlertProjection;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.AlertEntityRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@AllArgsConstructor
@Repository
public class AlertRepositoryImpl implements AlertRepository {
    private final AlertEntityRepositoryJpa alertEntityRepository;
    private final AlertMapper alertMapper;
    @Override
    public void create(Alert alert) {
        AlertEntity entity = alertMapper.toEntity(alert);
        alertEntityRepository.save(entity);
    }

    @Override
    public List<ResponseAlert> findTop10AlertAllByUser(Long userId) {
        List<ResponseAlertProjection> top10Alerts = alertEntityRepository.findTop10ByUserId(userId);
        return top10Alerts.stream()
                .map(s -> ResponseAlert.builder()
                        .alertType(s.getAlertType())
                        .createdAt(s.getCreatedAt())
                        .description(s.getDescription())
                        .latitude(s.getLatitude())
                        .longitude(s.getLongitude())
                        .build()
                )
                .toList(); // Recolecta los elementos transformados en una lista
    }

    @Override
    public List<ResponseAlert> findAllByUserId(Long userId) {
        List<ResponseAlertProjection> findAll = alertEntityRepository.findAll(userId);
        return findAll.stream()
                .map(s -> ResponseAlert.builder()
                        .alertType(s.getAlertType())
                        .createdAt(s.getCreatedAt())
                        .description(s.getDescription())
                        .latitude(s.getLatitude())
                        .longitude(s.getLongitude())
                        .build()
                )
                .toList(); // Recolecta los elementos transformados en una
    }

}
