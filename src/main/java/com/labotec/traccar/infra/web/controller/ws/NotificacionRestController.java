package com.labotec.traccar.infra.web.controller.ws;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.NotificationEntityRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class NotificacionRestController {

    private final NotificationEntityRepository notificationEntityRepository;
    private final NotificationService notificationService;



    @GetMapping("/notificacion")
    public Object enviarNotificacion() {

        return notificationEntityRepository.findAll();
    }
}
