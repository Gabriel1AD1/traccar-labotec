package com.labotec.traccar.infra.web.controller.ws;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/messages")
public class WebSocketController {
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // Endpoint para enviar mensajes desde Postman
    @PostMapping("/send-to-user")
    public void sendMessageToUser(
            @RequestHeader("user_id") String userId,
            @RequestHeader("message") String message) {
        System.out.println("Enviando mensaje a usuario: " + userId + " -> " + message);
        messagingTemplate.convertAndSendToUser(userId, "/queue/messages", message);
    }




}
