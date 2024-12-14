package com.labotec.traccar.infra.web.controller.rest.traccar;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.TypeSensor;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.process.DeviceEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.process.ParameterEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.process.RuleEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.DeviceRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DeviceSetupRunner implements CommandLineRunner {

    private DeviceRepositoryJpa deviceRepository;

    @Override
    public void run(String... args) throws Exception {
        if (false){


            DeviceEntity device = new DeviceEntity(12345L, 67890L);

            ParameterEntity door1 = new ParameterEntity("door1", TypeSensor.DOOR,"Sensor de puerta izquierda","boolean", true);
            door1.addRule(new RuleEntity("door1", "==", 1.0, "Puerta 1 abierta"));

            ParameterEntity speed = new ParameterEntity("speed", TypeSensor.SPEED, "Velocidad del automovil","float", true);
            speed.addRule(new RuleEntity("speed", ">", 120, "Exceso de velocidad detectado"));
            speed.addRule(new RuleEntity("duration", ">", 300, "Exceso de velocidad prolongado"));

            ParameterEntity temperature = new ParameterEntity("temperature", TypeSensor.TEMPERATURE ,"Sensor de temperatura de la puerta izquierda", "float", false);
            temperature.addRule(new RuleEntity("temperature", "<", -10, "Temperatura muy baja detectada"));

            device.addParameter(door1);
            device.addParameter(speed);
            device.addParameter(temperature);

            deviceRepository.save(device);

            System.out.println("Dispositivo creado y guardado en la base de datos con éxito.");
        }

    }
}