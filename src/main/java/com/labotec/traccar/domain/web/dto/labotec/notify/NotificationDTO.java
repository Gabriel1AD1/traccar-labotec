package com.labotec.traccar.domain.web.dto.labotec.notify;
import lombok.Data;

@Data
public class NotificationDTO {
    private Long userId;
    private String title;
    private String message;
}
