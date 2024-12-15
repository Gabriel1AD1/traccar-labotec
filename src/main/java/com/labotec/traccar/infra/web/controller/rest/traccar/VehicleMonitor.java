package com.labotec.traccar.infra.web.controller.rest.traccar;

import com.labotec.traccar.app.ports.input.email.GoogleEmail;
import com.labotec.traccar.domain.web.traccar.DeviceRequestDTO;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.NotificationEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.process.DoorStatusEntity;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.entity.process.DoorStatusId;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.DeviceAlertRepository;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.DoorStatusRepository;
import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.NotificationEntityRepository;
import com.labotec.traccar.infra.db.mysql.jpa.traccar.repository.TcDeviceRepository;
import com.labotec.traccar.infra.web.controller.ws.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;

@Service
@AllArgsConstructor
public class VehicleMonitor {
    private final NotificationService notificationService;
    private final TcDeviceRepository deviceRepository;
    private final DeviceAlertRepository alertRepository;
    private final GoogleEmail emailSender;
    private static final int ALERT_THRESHOLD_MINUTES = 1;
    private final DoorStatusRepository doorStatusRepository;
    private final NotificationEntityRepository notificationEntityRepository;
    enum VehicleStateChange {
        MOVING_TO_STOPPED,
        STOPPED_TO_MOVING,
        CONTINUES_STOPPED,
        UNKNOWN
    }

    @Transactional
    public void processPositionData(DeviceRequestDTO deviceRequestDTO) {
        long deviceId = deviceRequestDTO.getDeviceId();
        double speed = deviceRequestDTO.getSpeed();

        boolean isMoving = speed > 0;
        boolean wasMoving = !deviceRepository.isVehicleStopped((int) deviceId);
        boolean isAlertSent = deviceRepository.isAlertSent((int) deviceId);

        VehicleStateChange stateChange = determineStateChange(wasMoving, isMoving);

        switch (stateChange) {
            case MOVING_TO_STOPPED:
                deviceRepository.markVehicleAsStopped((int) deviceId, Instant.now());
                break;

            case STOPPED_TO_MOVING:
                handleStoppedToMoving(deviceId, deviceRequestDTO);
                deviceRepository.resetVehicleState((int) deviceId);
                break;

            case CONTINUES_STOPPED:
                if (!isAlertSent) {
                    handleContinuesStopped(deviceId, deviceRequestDTO);
                }
                break;

            default:
                System.out.println("Estado desconocido o sin cambios en el estado del vehículo.");
        }
    }

    private VehicleStateChange determineStateChange(boolean wasMoving, boolean isMoving) {
        if (wasMoving && !isMoving) return VehicleStateChange.MOVING_TO_STOPPED;
        else if (!wasMoving && isMoving) return VehicleStateChange.STOPPED_TO_MOVING;
        else if (!wasMoving) return VehicleStateChange.CONTINUES_STOPPED;
        else return VehicleStateChange.UNKNOWN;
    }

    private void handleContinuesStopped(long deviceId, DeviceRequestDTO deviceRequestDTO) {
        Instant lastStoppedTime = deviceRepository.getLastStoppedTime((int) deviceId);
        if (lastStoppedTime != null) {
            long minutesStopped = Duration.between(lastStoppedTime, Instant.now()).toMinutes();
            if (minutesStopped >= ALERT_THRESHOLD_MINUTES) {
                sendAlert(deviceRequestDTO, minutesStopped);
                deviceRepository.setAlertSentTrue((int) deviceId);
            }
        }
    }

    private void handleStoppedToMoving(long deviceId, DeviceRequestDTO deviceRequestDTO) {
        Instant lastStoppedTime = deviceRepository.getLastStoppedTime((int) deviceId);
        if (lastStoppedTime != null) {
            long minutesStopped = Duration.between(lastStoppedTime, Instant.now()).toMinutes();
            if (minutesStopped >= ALERT_THRESHOLD_MINUTES) {
                sendAlert(deviceRequestDTO, minutesStopped);
            }
        }
    }

    public void processDoorStatus(DeviceRequestDTO deviceRequestDTO) {
        long deviceId = deviceRequestDTO.getDeviceId();

        deviceRequestDTO.getAttributes().forEach((key, value) -> {
            if (key.startsWith("d_")) { // Procesa solo las puertas
                int doorState;

                // Verificación del tipo de `value` y casteo adecuado
                if (value instanceof Double) {
                    doorState = ((Double) value).intValue(); // Convertir Double a Integer
                } else if (value instanceof Integer) {
                    doorState = (Integer) value; // Castear directamente si es Integer
                } else {
                    System.out.println("Tipo de dato inesperado en puerta: " + key + " - valor: " + value);
                    return; // Ignorar si el tipo no es esperado
                }

                String doorId = key;
                DoorStatusId doorStatusId = new DoorStatusId(deviceId, doorId);

                // Busca el estado de la puerta en la base de datos
                DoorStatusEntity doorStatus = doorStatusRepository.findById(doorStatusId).orElse(null);

                Instant now = Instant.now();
                if (doorStatus != null) {
                    if (doorStatus.getState() == 0 && doorState == 1) {
                        // Evento: Cerrado a Abierto
                        // La puerta se ha abierto, registrar la hora de apertura
                        doorStatus.setState(doorState);
                        doorStatus.setLastChangeTime(now);
                        doorStatus.setAlertSent(false); // Reiniciar alerta para el evento "abierto a abierto"
                        doorStatusRepository.save(doorStatus);
                        System.out.println("Puerta " + doorId + " abierta.");

                    } else if (doorStatus.getState() == 1 && doorState == 1) {
                        // Evento: Abierto a Abierto (mientras permanece abierta)
                        // Calcular tiempo abierto y enviar alerta si supera el umbral
                        long minutesOpen = Duration.between(doorStatus.getLastChangeTime(), now).toMinutes();
                        if (minutesOpen > ALERT_THRESHOLD_MINUTES && !doorStatus.isAlertSent()) {
                            sendDoorAlert(deviceRequestDTO, doorId, minutesOpen);
                            doorStatus.setAlertSent(true); // Marcar que la alerta fue enviada
                            doorStatusRepository.save(doorStatus);
                        }

                    } else if (doorStatus.getState() == 1 && doorState == 0) {
                        // Evento: Abierto a Cerrado
                        // Calcular tiempo abierto y enviar alerta si supera el umbral
                        long minutesOpen = Duration.between(doorStatus.getLastChangeTime(), now).toMinutes();
                        if (minutesOpen > ALERT_THRESHOLD_MINUTES) {
                            sendDoorAlert(deviceRequestDTO, doorId, minutesOpen);
                        }
                        // Actualizar estado de la puerta y reiniciar alerta
                        doorStatus.setState(doorState);
                        doorStatus.setLastChangeTime(now);
                        doorStatus.setAlertSent(false); // Resetear para futuros eventos
                        doorStatusRepository.save(doorStatus);
                        System.out.println("Puerta " + doorId + " cerrada.");
                    }
                } else {
                    // Si no existe en la base de datos, crea un nuevo registro
                    doorStatusRepository.save(new DoorStatusEntity(deviceId, doorId, doorState, now));
                    System.out.println("Nuevo estado de puerta registrado para " + doorId + ".");
                }
            }
        });
    }





    private void sendDoorAlert(DeviceRequestDTO deviceRequestDTO, String doorId, long minutesOpen) {
        String description = "La puerta " + doorId + " ha estado abierta durante " + minutesOpen + " minutos";
        emailSender.sendEmail("cerroteberes@gmail.com", "ALERTA PUERTA ABIERTA", description);
        notificationService.sendNotification(description);
        notificationEntityRepository.save(new NotificationEntity(
                "ALERTA" ,
                description
        ));
        System.out.println("Alerta de puerta abierta registrada: " + description);
    }

    private void sendAlert(DeviceRequestDTO deviceRequestDTO, long minutesStopped) {
        String description = "El vehículo ha estado detenido durante " + minutesStopped + " minutos.";
        emailSender.sendEmail("cerroteberes@gmail.com", "ALERTA VEHÍCULO DETENIDO", description);
        notificationService.sendNotification(description);
        notificationEntityRepository.save(new NotificationEntity(
                "ALERTA" ,
                description
        ));
        System.out.println("Alerta de detención prolongada registrada: " + description);
    }
}
