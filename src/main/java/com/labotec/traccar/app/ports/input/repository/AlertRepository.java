package com.labotec.traccar.app.ports.input.repository;

import com.labotec.traccar.domain.database.models.Alert;
import com.labotec.traccar.domain.web.dto.labotec.response.ResponseAlert;

import java.util.List;

public interface AlertRepository {
    void create(Alert alert);
    List<ResponseAlert> findTop10AlertAllByUser(Long userId);
    List<ResponseAlert> findAllByUserId(Long userId);
}
