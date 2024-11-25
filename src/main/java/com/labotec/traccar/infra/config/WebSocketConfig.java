package com.labotec.traccar.infra.config;

import com.labotec.traccar.infra.web.controller.ws.LogWebSocketHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
@AllArgsConstructor
public class WebSocketConfig   implements WebSocketMessageBrokerConfigurer , WebSocketConfigurer{
    private final LogWebSocketHandler logWebSocketHandler;


    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); // Broker para enviar mensajes a los clientes
        config.setApplicationDestinationPrefixes("/app"); // Prefijo para mensajes desde el cliente
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOrigins("http://127.0.0.1:5500", "http://localhost:5500")
                .withSockJS();
    }
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(logWebSocketHandler, "/ws/logs").setAllowedOrigins("*");
    }
}
