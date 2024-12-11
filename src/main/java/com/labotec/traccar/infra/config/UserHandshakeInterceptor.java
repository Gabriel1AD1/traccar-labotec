package com.labotec.traccar.infra.config;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.security.Principal;
import java.util.Map;

public class UserHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) {
        String userId = request.getURI().getQuery().split("=")[1]; // Extraer el userId de la URL
        if (userId != null) {
            attributes.put("principal", (Principal) () -> userId); // Configurar Principal
            System.out.println("Interceptor: Usuario conectado con ID: " + userId);
        } else {
            System.out.println("No se encontró el userId en la URL.");
        }
        return true;
    }





    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
    }
}
