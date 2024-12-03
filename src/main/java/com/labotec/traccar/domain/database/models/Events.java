package com.labotec.traccar.domain.database.models;

import java.time.Instant;

public class Events {
    private Long id;
    private String nameEvent;
    private String descriptionEvent;
    private Long deviceId;
    private Long scheduleId;
    private Instant timeStamp;
    private String type;
}
