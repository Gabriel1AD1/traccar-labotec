package com.labotec.traccar.infra.web.controller.ws;

import com.labotec.traccar.infra.websockets.WebSocketService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/messages")
@AllArgsConstructor
public class WebSocketController {
    private final SimpMessagingTemplate messagingTemplate;
    private final WebSocketService webSocketService;

    // Endpoint para enviar mensajes desde Postman
    @PostMapping("/send-to-user")
    public void sendMessageToUser(
            @RequestHeader("user_id") String userId,
            @RequestHeader("message") String message) {
        System.out.println("Enviando mensaje a usuario: " + userId + " -> " + message);
        webSocketService.sendMessageToUser(userId, message);
    }




}
