package com.labotec.traccar.app.ports.out.services;

import com.labotec.traccar.domain.web.dto.labotec.response.ResponseAlert;

import java.util.List;

public interface AlertService {
    List<ResponseAlert> findAll(Long userId);
    List<ResponseAlert> findAllTop10(Long userId);
}
