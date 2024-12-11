package com.labotec.traccar.infra.web.controller.rest.labotec;

import com.labotec.traccar.infra.db.mysql.jpa.labotec.repository.NotificationEntityRepository;
import com.labotec.traccar.infra.web.controller.ws.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class NotificationController {

    private final NotificationEntityRepository notificationEntityRepository;
    private final NotificationService notificationService;

    @GetMapping("/notify")
    public Object findAllNotification() {
        return notificationEntityRepository.findAll();
    }
}
