package com.labotec.traccar.infra.websockets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        // Intentar obtener el userId desde los atributos
        String userId = null;
        if (headerAccessor.getSessionAttributes() != null) {
            userId = (String) headerAccessor.getSessionAttributes().get("user");
        }

        // Si los atributos son nulos, obtener el userId desde el Principal
        if (userId == null && headerAccessor.getUser() != null) {
            userId = headerAccessor.getUser().getName();
        }

        String sessionId = headerAccessor.getSessionId();

        if (userId != null && sessionId != null) {
            WebSocketSessionRegistry.addSession(userId, sessionId);
            logger.info("Conexión establecida: Usuario ID = {}, Session ID = {}", userId, sessionId);
        } else {
            logger.warn("No se pudo obtener el usuario o la sesión.");
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();
        WebSocketSessionRegistry.removeSession(sessionId);
        logger.info("Sesión desconectada: Session ID = {}", sessionId);
    }
}
