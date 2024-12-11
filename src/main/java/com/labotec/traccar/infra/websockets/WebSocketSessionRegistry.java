package com.labotec.traccar.infra.websockets;

import lombok.Getter;

import java.util.concurrent.ConcurrentHashMap;

public class WebSocketSessionRegistry {
    @Getter
    private static final ConcurrentHashMap<String, String> userSessionMap = new ConcurrentHashMap<>();

    public static void addSession(String userId, String sessionId) {
        userSessionMap.put(userId, sessionId);
    }

    public static void removeSession(String sessionId) {
        userSessionMap.values().removeIf(existingSessionId -> existingSessionId.equals(sessionId));
    }

    public static String getSessionId(String userId) {
        return userSessionMap.get(userId);
    }

}
