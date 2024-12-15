package com.labotec.traccar.app.implementation;

import com.labotec.traccar.app.ports.input.repository.AlertRepository;
import com.labotec.traccar.app.ports.out.services.AlertService;
import com.labotec.traccar.domain.web.labotec.response.ResponseAlert;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class AlertServiceI implements AlertService {
    private final AlertRepository alertRepository;
    @Override
    public List<ResponseAlert> findAll(Long userId) {
        return alertRepository.findAllByUserId(userId);
    }

    @Override
    public List<ResponseAlert> findAllTop10(Long userId) {
        return alertRepository.findTop10AlertAllByUser(userId);
    }
}
