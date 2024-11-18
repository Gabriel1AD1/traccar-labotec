package com.labotec.traccar.infra.web.controller.rest.traccar;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.process.DeviceEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.process.ParameterEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.process.RuleEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.DeviceRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@AllArgsConstructor
public class DeviceEvaluationService {

    private final DeviceRepositoryJpa deviceRepository;

    @Transactional
    public void evaluateDeviceByTraccarId(Long traccarDeviceId, Map<String, Object> values) {
        DeviceEntity device = deviceRepository.findByTraccarDeviceId(traccarDeviceId)
                .orElseThrow(() -> new IllegalArgumentException("Dispositivo no encontrado para el ID Traccar: " + traccarDeviceId));

        if (!validateParameters(device, values)) {
            System.out.println("Faltan parámetros requeridos. No se puede proceder con la evaluación.");
            return;
        }

        System.out.println("Evaluando parámetros del dispositivo con Traccar ID: " + traccarDeviceId);
        for (ParameterEntity parameter : device.getParameters()) {
            evaluateParameter(parameter, values);
        }
    }

    private boolean validateParameters(DeviceEntity device, Map<String, Object> values) {
        for (ParameterEntity parameter : device.getParameters()) {
            if (parameter.isRequired() && !values.containsKey(parameter.getName())) {
                System.out.println("Parámetro requerido faltante: " + parameter.getName());
                return false;
            }
        }
        return true;
    }

    private void evaluateParameter(ParameterEntity parameter, Map<String, Object> values) {
        System.out.println("Evaluando " + parameter.getName() + " con valores: " + values);
        for (RuleEntity rule : parameter.getRules()) {
            if (rule.evaluate(values)) {
                System.out.println("Alerta: " + rule.getAlertMessage());
            }
        }
    }
}

