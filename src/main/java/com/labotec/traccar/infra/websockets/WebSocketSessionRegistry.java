package com.labotec.traccar.infra.websockets;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

public class WebSocketSessionRegistry {
    @Getter
    private static final ConcurrentHashMap<String, String> userSessionMap = new ConcurrentHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(WebSocketSessionRegistry.class);

    public static void addSession(String userId, String sessionId) {
        logger.info("Registrando sesión: userId={}, sessionId={}", userId, sessionId);

        userSessionMap.put(userId, sessionId);
    }

    public static void removeSession(String sessionId) {
        userSessionMap.values().removeIf(existingSessionId -> existingSessionId.equals(sessionId));
    }

    public static String getSessionId(String userId) {
        return userSessionMap.get(userId);
    }

}
