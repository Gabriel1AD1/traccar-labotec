package com.labotec.traccar.infra.websockets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketService.class);

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendMessageToUser(String userId, String message) {
        String sessionId = WebSocketSessionRegistry.getSessionId(userId);
        if (sessionId != null) {
            messagingTemplate.convertAndSendToUser(userId, "/queue/messages", message);
            logger.info("Mensaje enviado a usuario {} (Session ID: {})", userId, sessionId);
        } else {
            logger.warn("Usuario {} no tiene una sesión activa.", userId);
        }
    }
}
