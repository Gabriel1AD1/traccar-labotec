package com.labotec.traccar.infra.websockets;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
@AllArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue"); // "/queue" para mensajes privados
        config.setApplicationDestinationPrefixes("/app"); // Prefijo para mensajes enviados desde el cliente
        config.setUserDestinationPrefix("/user"); // Prefijo para mensajes dirigidos a usuarios específicos
    }



    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setHandshakeHandler(new CustomHandshakeHandler()) // Configurar HandshakeHandler personalizado
                .setAllowedOrigins("http://127.0.0.1:5500", "http://localhost:5500")
                .withSockJS()
                .setInterceptors(new UserHandshakeInterceptor()); // Registra el interceptor
    }
}
