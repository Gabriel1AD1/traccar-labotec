package com.labotec.traccar.infra.websockets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.security.Principal;
import java.util.Map;
/**
 * UserHandshakeInterceptor es una implementación de {@link HandshakeInterceptor} que intercepta
 * el proceso de handshake de WebSocket para asociar un identificador único de usuario (userId)
 * a la conexión.
 *
 * <p>Esta clase extrae el `userId` de la URL de la solicitud entrante y lo almacena como
 * un atributo de la sesión. Además, configura un objeto {@link Principal} basado en el `userId`,
 * que es utilizado por Spring para enrutar mensajes privados a los usuarios correctos.
 *
 * <p>Este interceptor es esencial para permitir que el servidor identifique a cada usuario
 * conectado y facilite la comunicación privada utilizando el prefijo "/user".
 *
 * <p>Debe registrarse en la configuración de WebSocket para que sea efectivo durante el proceso de handshake.
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
 *                 .setInterceptors(new UserHandshakeInterceptor())
 *                 .withSockJS();
 *     }
 * }
 * }</pre>
 */
public class UserHandshakeInterceptor implements HandshakeInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(UserHandshakeInterceptor.class);

    /**
     * Intercepta el proceso de handshake antes de que la conexión WebSocket sea establecida.
     *
     * <p>Este método extrae el `userId` de la URL de la solicitud entrante y lo almacena
     * como un atributo de la sesión. También configura un objeto {@link Principal} basado
     * en el `userId` para permitir que Spring gestione destinos privados.
     *
     * @param request   La solicitud HTTP inicial del cliente.
     * @param response  La respuesta HTTP al cliente.
     * @param wsHandler El manejador de WebSocket asociado.
     * @param attributes Los atributos de la sesión donde se almacenan los datos del usuario.
     * @return {@code true} si el handshake puede continuar, {@code false} en caso contrario.
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) {
        String userId = request.getURI().getQuery().split("=")[1]; // Extraer el userId de la URL
        if (userId != null) {
            attributes.put("user", userId); // Asociar el userId a los atributos
            Principal principal = new Principal() {
                @Override
                public String getName() {
                    return userId;
                }
            };
            attributes.put("principal", principal); // Asociar Principal a los atributos
            logger.info("Interceptor: Usuario conectado con ID: {}", userId);
        } else {
            logger.warn("No se encontró el userId en la URL.");
        }
        return true;
    }

    /**
     * Método que se ejecuta después de que el handshake de WebSocket ha sido completado.
     *
     * <p>Actualmente, este método no realiza ninguna operación adicional.
     *
     * @param request   La solicitud HTTP inicial del cliente.
     * @param response  La respuesta HTTP al cliente.
     * @param wsHandler El manejador de WebSocket asociado.
     * @param exception Excepción ocurrida durante el handshake, si aplica.
     */
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
        // Nada adicional después del handshake
    }
}
