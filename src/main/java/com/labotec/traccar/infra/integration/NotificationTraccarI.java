package com.labotec.traccar.infra.integration;

import com.labotec.traccar.app.ports.input.notification.NotificationTraccar;
import com.labotec.traccar.domain.web.dto.labotec.notify.NotificationDTO;
import com.labotec.traccar.infra.websockets.WebSocketService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class NotificationTraccarI implements NotificationTraccar {
    private final WebSocketService webSocketService;
    @Override
    public void sendNotification(NotificationDTO notificationDTO) {
        String message = notificationDTO.getMessage();
        Long userId = notificationDTO.getUserId();
        String title = notificationDTO.getTitle();
        String finalMessage = title + " " + message;
        webSocketService.sendMessageToUser(String.valueOf(userId),finalMessage);
    }
}
