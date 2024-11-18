package com.labotec.traccar.infra.web.controller.rest.traccar;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
public class EvaluationRunner implements CommandLineRunner {

    private DeviceEvaluationService deviceEvaluationService;

    @Override
    public void run(String... args) throws Exception {
        if (false){
            Map<String, Object> values = new HashMap<>();
            values.put("door1", false);
            values.put("speed", null);
            values.put("temperature", 20);

            deviceEvaluationService.evaluateDeviceByTraccarId(12345L, values);
        }

    }
}
