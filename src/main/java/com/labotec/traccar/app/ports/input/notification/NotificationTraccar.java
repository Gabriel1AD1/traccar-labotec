package com.labotec.traccar.app.ports.input.notification;

import com.labotec.traccar.domain.web.dto.labotec.notify.NotificationDTO;

public interface NotificationTraccar {
     void sendNotification(NotificationDTO notificationDTO);
}
