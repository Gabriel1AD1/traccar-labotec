package com.labotec.traccar.app.ports.input.repository;


import com.labotec.traccar.domain.database.models.AlertValidParams;

import java.util.List;

public interface AlertValidParamsRepository {
    AlertValidParams findById(Long id);

    List<AlertValidParams> findByDeviceId(Long deviceId);

    void save(AlertValidParams alertValidParams);

    void deleteById(Long id);
}
