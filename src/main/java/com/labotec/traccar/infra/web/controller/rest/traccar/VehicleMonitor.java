package com.labotec.traccar.infra.web.controller.rest.traccar;

import com.labotec.traccar.app.usecase.ports.input.email.GoogleEmail;
import com.labotec.traccar.app.utils.mail.EmailSender;
import com.labotec.traccar.domain.web.dto.traccar.DeviceRequestDTO;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.DeviceAlertEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.DeviceAlertRepository;
import com.labotec.traccar.infra.db.mysql.jpa.traccar.repository.TcDeviceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;

@Service
@AllArgsConstructor
public class VehicleMonitor {

    private final TcDeviceRepository deviceRepository;
    private final DeviceAlertRepository alertRepository; // Repositorio para alertas
    private final GoogleEmail emailSender;
    private static final int ALERT_THRESHOLD_MINUTES = 1; // Umbral de tiempo para enviar la alerta

    @Transactional
    public void processPositionData(DeviceRequestDTO deviceRequestDTO) {
        // Extrae el ID del dispositivo y la velocidad del objeto DeviceRequestDTO
        long deviceId = deviceRequestDTO.getDeviceId();
        double speed = deviceRequestDTO.getSpeed();

        // Determina si el vehículo está en movimiento basado en la velocidad:
        // Si speed > 0, está en movimiento, de lo contrario, está detenido.
        boolean isMoving = speed > 0;

        // `wasMoving` indica el estado anterior del vehículo:
        // Utiliza el método `isVehicleStopped` para determinar si el vehículo estaba previamente detenido.
        // `isVehicleStopped` devuelve true si estaba detenido, por lo tanto, usamos ! para obtener `wasMoving`.
        boolean wasMoving = !deviceRepository.isVehicleStopped((int) deviceId);

        // **Primera condición: Cambio de estado de "en movimiento" a "detenido"**
        if (wasMoving && !isMoving) {
            // Si el vehículo estaba previamente detenido (`wasMoving = false`) y ahora está en movimiento (`isMoving = true`):
            // Esto indica un cambio de estado de detención a movimiento.

            // 1. Obtiene el `lastStoppedTime`, es decir, el tiempo en que el vehículo comenzó a estar detenido.
            Instant lastStoppedTime = deviceRepository.getLastStoppedTime((int) deviceId);

            // 2. Si `lastStoppedTime` no es nulo, calcula el tiempo total de detención.
            if (lastStoppedTime != null) {
                // Calcula la duración de la detención en minutos comparando `lastStoppedTime` con el tiempo actual (`Instant.now()`).
                long minutesStopped = Duration.between(lastStoppedTime, Instant.now()).toMinutes();

                // 3. Si el tiempo de detención es igual o superior al umbral (`ALERT_THRESHOLD_MINUTES`):
                // a. Llama al método `sendAlert` para registrar y enviar una alerta de detención prolongada.
                if (minutesStopped >= ALERT_THRESHOLD_MINUTES) {
                    sendAlert(deviceRequestDTO, minutesStopped);
                }
            }

            // 4. Reinicia el estado del vehículo en la base de datos:
            // Esto marca al vehículo como en movimiento y restablece `lastStoppedTime` y `alertSent`.
            deviceRepository.resetVehicleState((int) deviceId);
            deviceRepository.markVehicleAsStopped((int) deviceId, Instant.now());

        } else if (!wasMoving && isMoving) { // **Segunda condición: Cambio de "detenido" a "en movimiento"**
            // Si el vehículo estaba previamente detenido (`wasMoving = false`) y ahora está en movimiento (`isMoving = true`):
            // Esto indica un cambio de estado de detención a movimiento.

            // 1. Obtiene el `lastStoppedTime`, es decir, el tiempo en que el vehículo comenzó a estar detenido.
            Instant lastStoppedTime = deviceRepository.getLastStoppedTime((int) deviceId);

            // 2. Si `lastStoppedTime` no es nulo, calcula el tiempo total de detención.
            if (lastStoppedTime != null) {
                // Calcula la duración de la detención en minutos comparando `lastStoppedTime` con el tiempo actual (`Instant.now()`).
                long minutesStopped = Duration.between(lastStoppedTime, Instant.now()).toMinutes();

                // 3. Si el tiempo de detención es igual o superior al umbral (`ALERT_THRESHOLD_MINUTES`):
                // a. Llama al método `sendAlert` para registrar y enviar una alerta de detención prolongada.
                if (minutesStopped >= ALERT_THRESHOLD_MINUTES) {
                    sendAlert(deviceRequestDTO, minutesStopped);
                }
            }

            // 4. Reinicia el estado del vehículo en la base de datos:
            // Esto marca al vehículo como en movimiento y restablece `lastStoppedTime` y `alertSent`.
            deviceRepository.resetVehicleState((int) deviceId);
        }
    }


    private void sendAlert(DeviceRequestDTO deviceRequestDTO, long minutesStopped) {

        DeviceAlertEntity deviceAlertEntity = new DeviceAlertEntity();
        deviceAlertEntity.setDeviceId(deviceRequestDTO.getDeviceId());
        deviceAlertEntity.setAlertTime(Instant.now());
        deviceAlertEntity.setAlertType("Detención prolongada");
        deviceAlertEntity.setDuration((int) minutesStopped);
        deviceAlertEntity.setLatitude(deviceRequestDTO.getLatitude());
        deviceAlertEntity.setLongitude(deviceRequestDTO.getLongitude());
        deviceAlertEntity.setSpeed(deviceRequestDTO.getSpeed());
        deviceAlertEntity.setCourse(deviceRequestDTO.getCourse());
        deviceAlertEntity.setAltitude(deviceRequestDTO.getAltitude());
        deviceAlertEntity.setAddress(deviceRequestDTO.getAddress());
        deviceAlertEntity.setAccuracy(deviceRequestDTO.getAccuracy());

        String description = "El vehículo ha estado detenido durante " + minutesStopped + " minutos.";
        deviceAlertEntity.setDescription(description);
        emailSender.sendEmail("cerroteberes@gmail.com", "ALERTA VEHICULO PARADO MAS DE 10 minutos" , "TANTO TIEMPO "+minutesStopped );
        // Guardar alerta en la base de datos
        alertRepository.save(deviceAlertEntity);
        System.out.println("Alerta registrada: " + description);
    }
}
