package com.labotec.traccar.infra.websockets;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
/**
 * CustomHandshakeHandler es una implementación personalizada de {@link DefaultHandshakeHandler}
 * que se utiliza para determinar el usuario (Principal) asociado a una conexión WebSocket.
 *
 * <p>Durante el proceso de handshake de WebSocket, esta clase extrae el `userId` de los
 * atributos de la sesión y lo utiliza para crear un objeto {@link Principal}, que
 * posteriormente puede ser utilizado por Spring para identificar al usuario en mensajes STOMP.
 *
 * <p>Esto permite que Spring enrute mensajes privados de forma eficiente a los usuarios correctos
 * utilizando el prefijo "/user".
 *
 * <p>Por ejemplo, un mensaje enviado a `/user/{userId}/queue/messages` será recibido únicamente
 * por el usuario especificado.
 *
 * <p>Esta clase asume que el `userId` ha sido configurado previamente en los atributos de la
 * sesión por un {@link org.springframework.web.socket.server.HandshakeInterceptor}.
 *
 * <p>Uso típico:
 * <ul>
 *     <li>Registrar esta clase en la configuración de WebSocket usando
 *         {@link org.springframework.web.socket.config.annotation.StompEndpointRegistry#setHandshakeHandler}.
 *     </li>
 * </ul>
 *
 * <p>Ejemplo de registro:
 * <pre>{@code
 * @Configuration
 * public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
 *
 *     @Override
 *     public void registerStompEndpoints(StompEndpointRegistry registry) {
 *         registry.addEndpoint("/ws")
 *                 .setAllowedOrigins("*")
 *                 .setHandshakeHandler(new CustomHandshakeHandler())
 *                 .withSockJS();
 *     }
 * }
 * }</pre>
 */
public class CustomHandshakeHandler extends DefaultHandshakeHandler {

    /**
     * Determina el usuario asociado a una conexión WebSocket.
     *
     * <p>Esta funcion es llamado durante el proceso de handshake de WebSocket y utiliza
     * los atributos de la sesión para obtener el `userId` que fue configurado previamente.
     *
     * @param request   La solicitud HTTP inicial del cliente.
     * @param wsHandler El manejador de WebSocket asociado.
     * @param attributes Los atributos de la sesión configurados por un interceptor de handshake.
     * @return Un objeto {@link Principal} que representa al usuario conectado.
     */
    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        String userId = (String) attributes.get("user");
        return new Principal() {
            @Override
            public String getName() {
                return userId;
            }
        };
    }
}
